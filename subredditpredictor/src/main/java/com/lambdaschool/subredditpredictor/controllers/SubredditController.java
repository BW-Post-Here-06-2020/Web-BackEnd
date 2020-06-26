package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.Subreddit;
import com.lambdaschool.subredditpredictor.services.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/subreddits")
public class SubredditController {
	@Autowired
	private SubredditService subService;

	@GetMapping(value = "", produces = {"application/json"})
	public ResponseEntity<?> getAllSubs() {
		List<Subreddit> allSubs = subService.findAllSubreddits();
		return new ResponseEntity<>(allSubs, HttpStatus.OK);
	}

	@PostMapping(value = "/subreddit", consumes = {"application/json"})
	public ResponseEntity<?> createNewSub(@Valid @RequestBody Subreddit newSub) {
		newSub.setSubid(0);
		newSub = subService.save(newSub);

		HttpHeaders resHeaders = new HttpHeaders();
		URI newSubURI = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{subid}")
			.buildAndExpand(newSub.getSubid())
			.toUri();
		resHeaders.setLocation(newSubURI);

		return new ResponseEntity<>(null, resHeaders, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/subreddit/{subid}")
	public ResponseEntity<?> deleteUserById(@PathVariable long subid) {
		subService.delete(subid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
