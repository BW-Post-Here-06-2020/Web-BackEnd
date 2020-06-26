package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.exceptions.ResourceFoundException;
import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.models.Subreddit;
import com.lambdaschool.subredditpredictor.repositories.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "subredditService")
public class SubredditServiceImpl implements SubredditService {
	@Autowired
	private SubredditRepository subRepo;

	@Override
	public List<Subreddit> findAllSubreddits() {
		List<Subreddit> allSubs = new ArrayList<>();

		subRepo
			.findAll()
			.iterator()
			.forEachRemaining(allSubs::add);

		return allSubs;
	}

	@Transactional
	@Override
	public Subreddit save(Subreddit sub) {
		if (sub.getPosts().size() > 0) {
			throw new ResourceFoundException("posts are not updated through subreddits");
		}

		return subRepo.save(sub);
	}

	@Override
	public void delete(long id) {
		subRepo
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("subreddit " + id + " not found"));
		subRepo.deleteById(id);
	}
}
