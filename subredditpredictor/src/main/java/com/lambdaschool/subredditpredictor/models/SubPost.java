package com.lambdaschool.subredditpredictor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "subposts", uniqueConstraints = {@UniqueConstraint(columnNames = {"subid", "postid"})})
public class SubPost extends Audit implements Serializable {
	@Id
	@ManyToOne
	@JoinColumn(name = "subid")
	@JsonIgnoreProperties(value = "posts", allowSetters = true)
	private Subreddit subreddit;

	@Id
	@ManyToOne
	@JoinColumn(name = "postid")
	@JsonIgnoreProperties(value = "subreddits", allowSetters = true)
	private Post post;

	public SubPost() {
	}

	public SubPost(Subreddit subreddit, Post post) {
		this.subreddit = subreddit;
		this.post = post;
	}

	public Subreddit getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(Subreddit subreddit) {
		this.subreddit = subreddit;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SubPost subPost = (SubPost) o;
		return subreddit.equals(subPost.subreddit) && post.equals(subPost.post);
	}

	@Override
	public int hashCode() {
		return Objects.hash(subreddit, post);
	}
}
