package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.models.User;

import java.util.List;

public interface UserService {
	List<User> findAll();

	User findUserById(long id);

	User save(User user);

	User update(long id, User user);

	void delete(long id);
}
