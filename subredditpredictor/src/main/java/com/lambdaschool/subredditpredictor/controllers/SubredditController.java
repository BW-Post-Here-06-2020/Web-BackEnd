package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.Subreddit;
import com.lambdaschool.subredditpredictor.services.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@DeleteMapping(value = "/subreddit/{subid}")
	public ResponseEntity<?> deleteUserById(@PathVariable long subid) {
		subService.delete(subid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
