package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.SubredditPredictorApplication;
import com.lambdaschool.subredditpredictor.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SubredditPredictorApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostServiceImplTest {
	@Autowired
	private PostService postService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void aFindAllPosts() {
		assertEquals(1, postService.findAllPosts().size());
	}

	@Test
	public void bSave() {
		List<UserRole> user2Roles = new ArrayList<>();
		user2Roles.add(new UserRole(new User(), new Role()));
		User user2 = new User("test user", "test password", "testemail2@email.com", user2Roles);
		user2.setUserid(5);
		Subreddit testSub = new Subreddit("r/bingus");
		testSub.setSubid(6);

		Post testPost = new Post(user2, "test title", "test body");
		Post savePost = postService.save(user2, testSub, testPost);

		assertEquals("r/bingus", savePost.getSubreddits().get(0).getSubreddit().getSubName());
	}

	@Test
	public void cDelete() {
		postService.delete(7);
		assertEquals(1, postService.findAllPosts().size());
	}
}