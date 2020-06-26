package com.lambdaschool.subredditpredictor.controllers;

import com.lambdaschool.subredditpredictor.models.Post;
import com.lambdaschool.subredditpredictor.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
	@Autowired
	private PostService postService;

	@GetMapping(value = "", produces = {"application/json"})
	public ResponseEntity<?> getAllPosts() {
		List<Post> allPosts = postService.findAllPosts();
		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}

	@DeleteMapping(value = "/post/{postid}")
	public ResponseEntity<?> deleteUserById(@PathVariable long postid) {
		postService.delete(postid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
