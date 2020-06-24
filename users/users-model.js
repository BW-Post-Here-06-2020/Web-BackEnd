const db = require("../database/dbConfig.js")

module.exports = {
    add,
    find,
    findBy,
    findById,
    getAll,
    Update,
    remove,
};

function getAll() {
    return db('users')
}

function find() {
    return db("users").select("id", "username").orderBy("id");
}

function findBy(filter) {
    return db("users").where(filter).orderBy("id");
}

async function add(user) {
    try {
        const [id] = await db("users").insert(user, "id");
        return findById(id);
        
    } catch (error) {
        throw error;
    }
}

function findById(id) {
    return db("users").where({
        id
    }).first();
}

async function Update(id, changes) {
    await db('users').where({ id }).update(changes);
    return findById(id);
  }

  function remove(id) {
    return db('users').where(id).del();
  }