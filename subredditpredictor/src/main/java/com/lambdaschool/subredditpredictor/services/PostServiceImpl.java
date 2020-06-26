package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.handlers.HelperFunctions;
import com.lambdaschool.subredditpredictor.models.Post;
import com.lambdaschool.subredditpredictor.models.SubPost;
import com.lambdaschool.subredditpredictor.models.Subreddit;
import com.lambdaschool.subredditpredictor.models.User;
import com.lambdaschool.subredditpredictor.repositories.PostRepository;
import com.lambdaschool.subredditpredictor.repositories.SubredditRepository;
import com.lambdaschool.subredditpredictor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "postService")
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SubredditRepository subRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private HelperFunctions helper;

	@Override
	public List<Post> findAllPosts() {
		List<Post> allPosts = new ArrayList<>();

		postRepo
			.findAll()
			.iterator()
			.forEachRemaining(allPosts::add);

		return allPosts;
	}

	@Transactional
	@Override
	public Post save(User user, Subreddit sub, Post post) {
		Post newPost = new Post();

		newPost.setTitle(post.getTitle());
		newPost.setPostBody(post.getPostBody());

		User dbUser = userRepo.findById(user.getUserid())
			.orElseThrow(() -> new ResourceNotFoundException("user id " + user.getUserid() + " not found"));
		newPost.setUser(dbUser);

		Subreddit dbSub = subRepo.findById(sub.getSubid())
			.orElseThrow(() -> new ResourceNotFoundException("subreddit id " + sub.getSubid() + " not found"));

		SubPost newSubPost = new SubPost();
		newSubPost.setPost(newPost);
		newSubPost.setSubreddit(dbSub);
		newPost.getSubreddits().add(newSubPost);

		return postRepo.save(newPost);
	}
}