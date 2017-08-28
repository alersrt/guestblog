package org.student.guestblog.service;

import org.student.guestblog.model.Message;
import org.student.guestblog.model.User;

import java.util.List;

public interface MessageService {
	void save(Message message, User user);
	Message findById(String id);
	void deleteById(String id);
	List<Message> findAll();
}
