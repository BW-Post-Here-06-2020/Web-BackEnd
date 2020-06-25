package com.lambdaschool.subredditpredictor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postid;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String postBody;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "post", allowSetters = true)
	private List<SubPost> subreddits = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
	@JsonIgnoreProperties(value = "posts", allowSetters = true)
	private User user;

	public Post() {
	}

	public Post(String title, String postBody) {
		setTitle(title);
		setPostBody(postBody);
	}

	public long getPostid() {
		return postid;
	}

	public void setPostid(long postId) {
		this.postid = postId;
	}

	public String getTitle() {
		if (title == null) {
			return null;
		} else {
			return title;
		}
	}

	public void setTitle(String title) {
		this.title = title.toUpperCase();
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	public List<SubPost> getSubreddits() {
		return subreddits;
	}

	public void setSubreddits(List<SubPost> subreddits) {
		this.subreddits = subreddits;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post{" +
			"postId=" + postid +
			", title='" + title + '\'' +
			", postBody='" + postBody + '\'' +
			", subreddits=" + subreddits +
			", user=" + user +
			'}';
	}
}
