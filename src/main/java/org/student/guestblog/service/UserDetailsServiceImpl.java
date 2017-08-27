package org.student.guestblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.student.guestblog.dao.UserDAO;
import org.student.guestblog.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findFirstByUsername(username.toLowerCase());

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		LOGGER.info("Security info: " + user.toString());
		user.getRoles().forEach(r -> grantedAuthorities.add(new SimpleGrantedAuthority(r.toString())));
		LOGGER.info(grantedAuthorities.toString());
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				grantedAuthorities
		);
	}
}
