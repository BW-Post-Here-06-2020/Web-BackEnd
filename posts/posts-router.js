const router = require('express').Router();
const Posts = require('./posts-model');

router.get('/user/:id', (req, res) => {
  Posts.findbyuser(req.params.id)
    .then((Posts) => {
      res.status(200).json(Posts);
    })
    .catch((err) => {
      res.status(500).json({ message: err.message });
    });
});

router.post('/', (req, res) => {
  //   const { id } = req.params;
  //   const newpost = req.body;
  Posts.add(req.body)
    .then((post) => {
      res.status(201).json({ created: post });
    })
    .catch((err) => {
      res.status(500).json({ message: 'error' });
    });
});

router.put('/:id', (req, res) => {
  const { id } = req.params;
  const changes = req.body;
  Posts.findbyid(id)
    .then((post) => {
      post
        ? Posts.update(id, changes).then((updated) => {
            res.status(200).json({
              message: `successfully updated post ID: ${id}`,
            });
          })
        : res.status(404).json({
            errorMessage: 'post not found',
          });
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({
        errorMessage: error.message,
      });
    });
});

router.delete('/:id', (req, res) => {
  const { id } = req.params;
  Posts.remove({ id })
    .then((deleted) => {
      res
        .status(200)
        .json({ message: `post ID: ${id} has been removed`, deleted });
    })
    .catch((err) => {
      console.log(err);
      res
        .status(500)
        .json({ errorMessage: `cannot remove post by ID: ${id}` });
    });
});

module.exports = router;