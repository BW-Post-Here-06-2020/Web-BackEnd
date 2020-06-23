package com.lambdaschool.subredditpredictor.repositories;

import com.lambdaschool.subredditpredictor.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
