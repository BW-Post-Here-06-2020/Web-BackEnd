package com.lambdaschool.subredditpredictor;

import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.models.User;
import com.lambdaschool.subredditpredictor.models.UserRole;
import com.lambdaschool.subredditpredictor.services.RoleService;
import com.lambdaschool.subredditpredictor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Override
	public void run(String[] args) throws Exception {
		Role r1 = new Role("admin");
		Role r2 = new Role("user");
		Role r3 = new Role("data");

		roleService.save(r1);
		roleService.save(r2);
		roleService.save(r3);

		List<UserRole> user1Roles = new ArrayList<>();
		user1Roles.add(new UserRole(new User(), r1));
		user1Roles.add(new UserRole(new User(), r2));
		User user1 = new User("admin", "password", user1Roles);
		userService.save(user1);
	}
}
