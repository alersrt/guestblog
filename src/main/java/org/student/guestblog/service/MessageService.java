package org.student.guestblog.service;

import org.student.guestblog.model.Message;

import java.util.List;

public interface MessageService {
	void save(Message message);
	void deleteById(String id);
	List<Message> findAll();
}
