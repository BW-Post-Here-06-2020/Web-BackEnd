const express = require('express')
const cors = require('cors')
const helmet = require('helmet')

const usersRouter = require('../auth/auth-router')
const postsRouter = require('../posts/posts-router.js')

const server = express()

server.use(helmet());
server.use(cors());
server.use(express.json());

server.use('/api/users', usersRouter)
server.use('/api/posts', postsRouter)

server.get("/", (req, res) => {
    res.json({ api: "up"})
})

module.exports = server