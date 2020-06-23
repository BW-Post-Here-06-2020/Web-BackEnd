package com.lambdaschool.subredditpredictor;

import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
	@Autowired
	private RoleService roleService;

	@Override
	public void run(String[] args) throws Exception {
		Role r1 = new Role("admin");
		Role r2 = new Role("user");
		Role r3 = new Role("data");

		roleService.save(r1);
		roleService.save(r2);
		roleService.save(r3);
	}
}
