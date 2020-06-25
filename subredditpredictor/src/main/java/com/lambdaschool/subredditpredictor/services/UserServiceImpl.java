package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.handlers.HelperFunctions;
import com.lambdaschool.subredditpredictor.models.*;
import com.lambdaschool.subredditpredictor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleService roleService;

	@Autowired
	private HelperFunctions helper;

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();

		userRepo
			.findAll()
			.iterator()
			.forEachRemaining(list::add);

		return list;
	}

	@Override
	public User findUserById(long id) {
		return userRepo
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("user " + id + " not found"));
	}

	@Transactional
	@Override
	public User save(User user) {
		User newUser = new User();

		if (user.getUserid() != 0) {
			User oldUser = userRepo
				.findById(user.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("user " + user.getUserid() + " not found"));
			oldUser.getRoles().clear();
			newUser.setUserid(user.getUserid());
		}

		newUser.setUsername(user.getUsername());
		newUser.setPasswordNoEncrypt(user.getPassword());
		newUser.setPrimaryEmail(user.getPrimaryEmail());

		newUser.getPosts().clear();
		if (user.getPosts().size() == 0) {
			for (Post p : user.getPosts()) {
				newUser.addPost(new Post(newUser, p.getTitle(), p.getPostBody()));
			}
		}

		newUser.getRoles().clear();
		if (user.getUserid() == 0) {
			for (UserRole ur : user.getRoles()) {
				Role newRole = roleService.findRoleById(ur.getRole().getRoleid());

				newUser.addRole(newRole);
			}
		}

		return userRepo.save(newUser);
	}

	@Transactional
	@Override
	public User update(long id, User user) {
		User currentUser = findUserById(id);

		if (helper.isAuthorizedToMakeChange(user.getUsername())) {
			if (user.getUsername() != null) {
				currentUser.setUsername(user.getUsername().toLowerCase());
			}

			if (user.getPassword() != null) {
				currentUser.setPasswordNoEncrypt(user.getPassword());
			}

			if (user.getPrimaryEmail() != null) {
				currentUser.setPrimaryEmail(user.getPrimaryEmail().toLowerCase());
			}

			if (user.getPosts().size() > 0) {
				currentUser.getPosts().clear();
				for (Post p : user.getPosts()) {
					currentUser.getPosts().add(new Post(currentUser, p.getTitle(), p.getPostBody()));
				}
			}

			if (user.getRoles().size() > 0) {
				currentUser.getRoles().clear();
				for (UserRole ur : user.getRoles()) {
					currentUser.getRoles().add(ur);
				}
			}
			return userRepo.save(currentUser);
		} else {
			throw new ResourceNotFoundException("user not authorized to make this change");
		}
	}
}
