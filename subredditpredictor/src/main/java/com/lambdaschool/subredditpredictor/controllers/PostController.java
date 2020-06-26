package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.handlers.HelperFunctions;
import com.lambdaschool.subredditpredictor.models.Post;
import com.lambdaschool.subredditpredictor.models.Role;
import com.lambdaschool.subredditpredictor.repositories.UserRepository;
import com.lambdaschool.subredditpredictor.services.PostService;
import com.lambdaschool.subredditpredictor.services.UserAudit;
import com.lambdaschool.subredditpredictor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserAudit userAudit;

	@GetMapping(value = "", produces = {"application/json"})
	public ResponseEntity<?> getAllPosts() {
		List<Post> allPosts = postService.findAllPosts();
		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}

	@PostMapping(value = "/post", consumes = {"application/json"})
	public ResponseEntity<?> createNewPost(@Valid @RequestBody Post newPost) {
		// List<HttpHeaders> headers = new ArrayList<>();
		newPost.setPostid(0);
		newPost = postService.save(userRepo.findByUsername(userAudit.getCurrentAuditor().get()), newPost.getSubreddits().get(0).getSubreddit(), newPost);

		HttpHeaders resHeaders = new HttpHeaders();
		URI newPostURI = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{postid}")
			.buildAndExpand(newPost.getPostid())
			.toUri();
		resHeaders.setLocation(newPostURI);

		return new ResponseEntity<>(null, resHeaders, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/post/{postid}")
	public ResponseEntity<?> deleteUserById(@PathVariable long postid) {
		postService.delete(postid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
