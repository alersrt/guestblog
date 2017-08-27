package org.student.guestblog.service;

public interface SecurityService {
	String findLoggedInUsername();
	void autoLogin(String username, String password);
}
