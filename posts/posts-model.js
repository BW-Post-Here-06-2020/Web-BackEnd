const db = require('../database/dbConfig');

async function add(posts) {
  const [id] = await db('posts').insert(posts);

  return findbyid(id);
}

function findby(filter) {
  return db('posts').where(filter);
}

function findbyid(id) {
  return db('posts').where({ id }).first();
}

function findbyuser(user_id) {
  return db('posts').where({ user_id });
}

function update(id, changes) {
  return db('posts').where({ id }).update(changes);
}

function remove(id) {
  return db('posts').where(id).del();
}
module.exports = {
  add,
  findby,
  findbyuser,
  findbyid,
  update,
  remove,
};