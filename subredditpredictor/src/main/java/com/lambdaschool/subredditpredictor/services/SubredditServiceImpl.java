package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.exceptions.ResourceFoundException;
import com.lambdaschool.subredditpredictor.models.Subreddit;
import com.lambdaschool.subredditpredictor.repositories.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "subredditService")
public class SubredditServiceImpl implements SubredditService {
	@Autowired
	private SubredditRepository subRepo;

	@Transactional
	@Override
	public Subreddit save(Subreddit sub) {
		if (sub.getPosts().size() > 0) {
			throw new ResourceFoundException("posts are not updated through subreddits");
		}

		return subRepo.save(sub);
	}
}
