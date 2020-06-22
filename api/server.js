const express = require('express')
const cors = require('cors')
const helmet = require('helmet')

const usersRouter = requre('../users/users-router.js')
const server = express()

server.use(helmet());
server.use(cors());
server.use(express.json());

server.use('/api/users', usersRouter)

module.exports = server