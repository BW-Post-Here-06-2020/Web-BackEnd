package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.models.UserRole;
import com.lambdaschool.subredditpredictor.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public List<Role> findAll() {
		List<Role> list = new ArrayList<>();

		roleRepo
			.findAll()
			.iterator()
			.forEachRemaining(list::add);
		return list;
	}

	@Override
	public Role findRoleById(long id) {
		return roleRepo
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("role " + id + " not found"));
	}

	@Transactional
	@Override
	public Role save(Role role) {
		// Role newRole = new Role();
		// newRole.setName(role.getName());
		// newRole.getUsers().clear();
		// for (UserRole ur : role.getUsers()) {
		//
		// }

		return roleRepo.save(role);
	}
}
