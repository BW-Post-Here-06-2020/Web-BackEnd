package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.models.User;
import com.lambdaschool.subredditpredictor.models.UserRole;
import com.lambdaschool.subredditpredictor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleService roleService;

	@Override
	public User save(User user) {
		User newUser = new User();

		// if statement

		newUser.setUsername(user.getUsername());
		newUser.setPasswordNoEncrypt(user.getPassword());
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
