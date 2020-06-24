package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.models.User;

import java.util.List;

public interface UserService {
	List<User> findAll();

	User findUserById(long id);

	User save(User user);
}
