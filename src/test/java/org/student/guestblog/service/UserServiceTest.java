package org.student.guestblog.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.student.guestblog.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/appconfig-root.xml"})
@WebAppConfiguration
@ActiveProfiles("dev")
public class UserServiceTest extends AbstractServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void addUser() {
		User newUser = new User("testmongol", "rjxtdybr", "nomad@barrens.hk");
		LOGGER.info("****************************************");
		LOGGER.info(newUser.toString());
		LOGGER.info("****************************************");
		User createdUser = userService.addUser(newUser);
		LOGGER.info("****************************************");
		LOGGER.info(createdUser.toString());
		LOGGER.info("****************************************");
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

		Assert.assertNotNull("The object is null: " + newUser.toString(), newUser);
		Assert.assertNotNull("The object is null: " + createdUser.toString(), createdUser);
		Assert.assertEquals("The User's objects is not equals", newUser, createdUser);
	}

	@Test
	public void findByUsername() {
		Assert.assertNotNull("The user does not found.", userService.findByUsername("testmongol"));
	}

	@Test
	public void deleteUser() {
		userService.deleteUser(userService.findByUsername("testmongol"));
		Assert.assertNull(userService.findByUsername("testmongol"));
	}

}
