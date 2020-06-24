package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.models.User;
import com.lambdaschool.subredditpredictor.models.UserRole;
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

		// if statement

		newUser.setUsername(user.getUsername());
		newUser.setPasswordNoEncrypt(user.getPassword());
		newUser.setPrimaryEmail(user.getPrimaryEmail());
		newUser.getRoles().clear();
		if (user.getUserId() == 0) {
			for (UserRole ur : user.getRoles()) {
				Role newRole = roleService.findRoleById(ur.getRole().getRoleId());

				newUser.addRole(newRole);
			}
		}

		return userRepo.save(newUser);
	}
}
