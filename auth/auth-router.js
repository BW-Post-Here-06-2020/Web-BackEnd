const bcryptjs = require("bcryptjs");
const jwt = require("jsonwebtoken");
const router = require("express").Router();

const Users = require("../users/users-model")
const { isValid } = require("../users/users-service")
const constants = require("../utils/constants")


//create Token Function 
function createToken(user){
    const payload = {
      subject: user.id,
      username: user.username,
    };
    const secret = constants.jwtSecret
    const options = {
      expiresIn: '10 min',
    }
    return jwt.sign(payload, secret, options)
  }

// auth function
  function auth(req, res, next) {
    jwt.verify(req.headers.authorization, secret.jwtSecret, function (
      err,
      decoded
    ) {
      if (err) {
        res.status(400).json({ error: 'not logged in' });
      } else {
        req.token = decoded;
        next();
      }
    });
  }
    

//Register new user
  router.post("/register", (req, res) => {
    const credentials = req.body;
  
    if (isValid(credentials)) {
      const rounds = process.env.BCRYPT_ROUNDS || 8;
      const hash = bcryptjs.hashSync(credentials.password, rounds);       // hash the password
      credentials.password = hash;

      Users.add(credentials)        // save the user to the database
        .then(user => {
          res.status(201).json({ data: user });
        })
        .catch(error => {
          res.status(500).json({ message: error.message });
        });
    } else {
      res.status(400).json({
        message: "please provide username and password and the password shoud be alphanumeric",
      });
    }
  });

//post/ login
router.post("/login", (req, res) => {
    const { username, password } = req.body;
    if (isValid(req.body)) {
      Users.findBy({ username: username })
        .then(([user]) => {
          // compare the password the hash stored in the database
          if (user && bcryptjs.compareSync(password, user.password)) {
            const token = createToken(user);
            res.status(200).json({ token, message: "Welcome to our API" });
          } else {
            res.status(401).json({ message: "Invalid credentials" });
          }
        })
        .catch(error => {
          res.status(500).json({ message: error.message });
        });
    } else {
      res.status(400).json({
        message: "please provide username and password and the password shoud be alphanumeric",
      });
    }
  });

  //get a list of users
  router.get('/', (req, res) => {
    User.find()
      .then((users) => {
        res.status(200).json(users);
      })
      .catch((err) => res.send(err));
  });

  //get a user by id
router.get('/:id', (req, res) => {
  User.findbyid(req.params.id)
    .then((user) => {
      res.status(200).json(user);
    })
    .catch((err) => {
      res.status(500).json({ err: err.message });
    });
});

//update user by id 
router.put('/:id', auth, (req, res) => {
  const { password, new_password, phone } = req.body;

  User.findbyid(req.params.id)
    .then((found) => {
      if (found && bcrypt.compareSync(password, found.password)) {
        const hash = bcrypt.hashSync(new_password, 12);
        User.update(req.params.id, {
          password: hash,
          phone,
        }).then((updatedUser) => {
          res.status(200).json({ message: 'updated user info', updatedUser });
        });
      } else {
        res.status(500).json({ err: 'unable to update password/phone' });
      }
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ message: err.message });
    });
});

//delete user by id 
router.delete('/:id', auth, (req, res) => {
  const { id } = req.params;
  User.remove({ id })
    .then((deleted) => {
      res.status(200).json({ message: 'user deleted', deleted });
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({ message: err.message });
    });
});
  
  module.exports = router;
  
