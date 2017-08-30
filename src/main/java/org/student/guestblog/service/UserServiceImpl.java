package org.student.guestblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.dao.RoleDAO;
import org.student.guestblog.dao.UserDAO;
import org.student.guestblog.model.Role;
import org.student.guestblog.model.User;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User addUser(User user) {
		user.setUsername(user.getUsername().toLowerCase());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = user.getRoles();
		roles.add(roleDAO.findByRolename("ROLE_USER"));
		user.setRoles(roles);
		LOGGER.info("UserService: " + user.toString());
		return userDAO.insert(user);
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
