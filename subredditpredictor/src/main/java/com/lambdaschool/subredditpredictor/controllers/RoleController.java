package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping(value = "", produces = {"application/json"})
	public ResponseEntity<?> getAllRoles() {
		List<Role> allRoles = roleService.findAll();
		return new ResponseEntity<>(allRoles, HttpStatus.OK);
	}
}
