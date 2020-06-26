package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.models.Subreddit;

import java.util.List;

public interface SubredditService {
	List<Subreddit> findAllSubreddits();

	Subreddit save(Subreddit subreddit);

	void delete(long id);
}
