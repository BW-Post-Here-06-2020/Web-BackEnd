package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.User;
import com.lambdaschool.subredditpredictor.models.UserRole;
import com.lambdaschool.subredditpredictor.services.RoleService;
import com.lambdaschool.subredditpredictor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OpenController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@PostMapping(value = "/createnewuser", consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> createNewUser(
		HttpServletRequest httpServletRequest,
		@Valid @RequestBody User newUserData
	) throws URISyntaxException {
		User newUser = new User();
		newUser.setUsername(newUserData.getUsername());
		newUser.setPassword(newUserData.getPassword());
		newUser.setPrimaryEmail(newUserData.getPrimaryEmail());
		List<UserRole> newUserRoles = new ArrayList<>();
		newUserRoles.add(new UserRole(newUser, roleService.findRoleByName("user")));
		newUser.setRoles(newUserRoles);

		newUser = userService.save(newUser);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUserURI = ServletUriComponentsBuilder
			.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userid}")
			.buildAndExpand(newUser.getUserId())
			.toUri();
		responseHeaders.setLocation(newUserURI);

		RestTemplate restTemplate = new RestTemplate();
		String requestURI = "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/login";

		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(acceptableMediaTypes);
		// headers.setBasicAuth(System.getenv("OAUTHCLIENTID"), System.getenv("OAUTHCLIENTSECRET"));
		headers.setBasicAuth("lambda-client", "lambda-secret");

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type","password");
		map.add("scope","read write trust");
		map.add("username", newUserData.getUsername());
		map.add("password", newUserData.getPassword());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		String theToken = restTemplate.postForObject(requestURI, request, String.class);

		return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
	}
}
