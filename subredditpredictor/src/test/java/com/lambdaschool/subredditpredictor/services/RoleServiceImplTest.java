package com.lambdaschool.subredditpredictor.services;

import com.lambdaschool.subredditpredictor.SubredditPredictorApplication;
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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SubredditPredictorApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceImplTest {
	@Autowired
	private RoleService roleService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void aTearDown() throws Exception {
	}

	@Test
	public void bFindAll() {
		assertEquals(3, roleService.findAll().size());
	}

	@Test
	public void cFindRoleById() {
		assertEquals("ADMIN", roleService.findRoleById(1).getName());
	}

	@Test
	public void dFindRoleByName() {
		assertEquals("USER", roleService.findRoleByName("user").getName());
	}

	@Test
	public void eSave() {
	}

	@Test
	public void fUpdate() {
	}

	@Test
	public void gDelete() {
		roleService.delete(3);
		assertEquals(2, roleService.findAll().size());
	}
}