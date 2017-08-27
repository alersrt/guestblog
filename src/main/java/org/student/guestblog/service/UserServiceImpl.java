package org.student.guestblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.dao.UserDAO;
import org.student.guestblog.model.Role;
import org.student.guestblog.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void addUser(User user) {
		user.setUsername(user.getUsername().toLowerCase());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(new Role());
		LOGGER.info("Roles: " + roles.toString());
		user.setRoles(roles);
		LOGGER.info("User[" + user.toString() + "] was added");
		userDAO.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userDAO.delete(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}
}
