package com.lambdaschool.subredditpredictor.repositories;

import com.lambdaschool.subredditpredictor.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
