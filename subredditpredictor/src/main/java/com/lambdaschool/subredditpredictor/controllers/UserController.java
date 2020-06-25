package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.User;
import com.lambdaschool.subredditpredictor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "", produces = {"application/json"})
	public ResponseEntity<?> getAllUsers() {
		List<User> allUsers = userService.findAll();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/user/{userid}", produces = {"application/json"})
	public ResponseEntity<?> getUserById(@PathVariable long userid) {
		User user = userService.findUserById(userid);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}


}
