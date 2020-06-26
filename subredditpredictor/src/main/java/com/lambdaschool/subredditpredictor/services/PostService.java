package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.models.Post;
import com.lambdaschool.subredditpredictor.models.Subreddit;
import com.lambdaschool.subredditpredictor.models.User;

import java.util.List;

public interface PostService {
	List<Post> findAllPosts();

	Post save(User user, Subreddit sub, Post post);
}
