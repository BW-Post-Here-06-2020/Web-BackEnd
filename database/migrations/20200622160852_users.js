exports.up = function (knex) {
    return knex.schema
      .createTable('users', (users) => {
        users.increments();
        users.string('first_name');
        users.string('last_name');
        users.string('username', 20).notNullable().unique();
        users.string('password', 20).notNullable();
        users.integer('phone', 11).notNullable();
      })

  };
  exports.down = function (knex) {
    return knex.schema.dropTableIfExists('posts').dropTableIfExists('users');
  };