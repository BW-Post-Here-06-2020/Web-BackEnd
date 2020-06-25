package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "", produces = {"application/json"})
	public ResponseEntity<?> getAllRoles() {
		List<Role> allRoles = roleService.findAll();
		return new ResponseEntity<>(allRoles, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/role/{roleid}", produces = {"application/json"})
	public ResponseEntity<?> getRoleById(@PathVariable long roleid) {
		Role role = roleService.findRoleById(roleid);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	@PostMapping(value = "/role", consumes = {"application/json"})
	public ResponseEntity<?> createNewRole(@Valid @RequestBody Role newRole) {
		newRole.setRoleid(0);
		newRole = roleService.save(newRole);

		HttpHeaders resHeaders = new HttpHeaders();
		URI newRoleURI = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{roleid}")
			.buildAndExpand(newRole.getRoleid())
			.toUri();
		resHeaders.setLocation(newRoleURI);

		return new ResponseEntity<>(null, resHeaders, HttpStatus.CREATED);
	}

	@PutMapping(value = "/role/{roleid}", consumes = {"application/json"})
	public ResponseEntity<?> updateFullRole(@PathVariable long roleid, @Valid @RequestBody Role role) {
		Role updateRole = roleService.update(roleid, role);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
