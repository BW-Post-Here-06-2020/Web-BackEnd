package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.models.Role;

import java.util.List;

public interface RoleService {
	List<Role> findAll();

	Role findRoleById(long id);

	Role findRoleByName(String name);

	Role save(Role role);
}
