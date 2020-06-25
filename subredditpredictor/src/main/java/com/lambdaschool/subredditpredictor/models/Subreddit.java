package com.lambdaschool.subredditpredictor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subreddits")
public class Subreddit extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long subId;

	@Column(nullable = false, unique = true)
	private String subName;

	@OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "subreddit", allowSetters = true)
	private List<SubPost> posts = new ArrayList<>();

	public Subreddit() {
	}

	public Subreddit(String subName, List<SubPost> subPosts) {
		setSubName(subName);
		for (SubPost sp : subPosts) {
			sp.setSubreddit(this);
		}
		this.posts = subPosts;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public String getSubName() {
		if (subName == null) {
			return null;
		} else {
			return subName.toLowerCase();
		}
	}

	public void setSubName(String subName) {
		this.subName = subName.toLowerCase();
	}

	public List<SubPost> getPosts() {
		return posts;
	}

	public void setPosts(List<SubPost> posts) {
		this.posts = posts;
	}

	public void addPost(Post post) {
		posts.add(new SubPost(this, post));
	}

	@Override
	public String toString() {
		return "Subreddit{" +
			"subId=" + subId +
			", subName='" + subName + '\'' +
			", posts=" + posts +
			'}';
	}
}
