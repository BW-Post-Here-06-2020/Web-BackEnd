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
  
      .createTable('posts', (posts) => {
        posts.increments();
        posts.string('name', 50).notNullable();
        posts.string('description', 225).notNullable();
        posts.string('date', 10).notNullable();
        posts
          .integer('user_id')
          .notNullable()
          .unsigned()
          .references('id')
          .inTable('users')
          .onDelete('CASCADE');
      });
  };
  
  exports.down = function (knex) {
    return knex.schema.dropTableIfExists('posts').dropTableIfExists('users');
  };