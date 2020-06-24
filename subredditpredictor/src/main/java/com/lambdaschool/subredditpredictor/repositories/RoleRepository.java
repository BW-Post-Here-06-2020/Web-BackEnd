package com.lambdaschool.subredditpredictor.repositories;

import com.lambdaschool.subredditpredictor.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByNameIgnoreCase(String name);
}
