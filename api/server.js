const express = require('express')
const cors = require('cors')
const helmet = require('helmet')

const usersRouter = require('../users/users-router.js')
const postsRouter = require('../posts/posts-router.js')

const server = express()

server.use(helmet());
server.use(cors());
server.use(express.json());

server.use('/api/users', usersRouter)
server.use('/api/posts', postsRouter)

module.exports = server