const server = require('../api/server')
const supertest = require('supertest')
const db = require('../database/dbConfig')

describe('auth-router', () => {
    // tests for registering a new user
    describe('POST/ register', () => {
        beforeEach(async () => {
            await db('users').truncate();
          });;
          
          it('should register a new user', async () => {
            return supertest(server)
              .post('/api/users/register')
              .send({ username: "ivan", password: "pass", phone: 12312 })
              .then(res => {
                expect(res.status).toBe(201);
              })
          })

          it('should return the hash password', async () => {
            return supertest(server)
            .post('/api/users/register')
            .send({ username: "ivan", password: "pass", phone: 12312})
            .then(res => {
              expect(res.body.data.password).not.toBe('pass');
            })
          })
    })

    //tests for login users 
    describe('POST to /api/auth/login', () => {
        it('should log in a user with credentials', async () => {
            return supertest(server)
            .post('/api/users/register')
            .send({ username: "ivan", password: "pass", phone: 12312})
            .then(res => {
                token = res.body.token
                return supertest(server)
                .post('/api/users/login')
                .send({ username: "ivan", password: "pass" })
                .then(res => {
                  expect(res.status).toBe(200);
                  expect(res.body.message).toBe('Welcome to our API');
                })
            })
        })
    })

    it('should not login if the user credentials are invalid' ,() => {
        return supertest(server)
          .post('/api/users/login')
          .send({ username: "ivan", password: "password"})
          .then(res => {
            expect(res.status).toBe(401);
            expect(res.body.message).toBe("Invalid credentials")
          })
      })

})
