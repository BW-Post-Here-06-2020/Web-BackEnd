package com.lambdaschool.subredditpredictor.repositories;

import com.lambdaschool.subredditpredictor.models.Subreddit;
import org.springframework.data.repository.CrudRepository;

public interface SubredditRepository extends CrudRepository<Subreddit, Long> {
}
