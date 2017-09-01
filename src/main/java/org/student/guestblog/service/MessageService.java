package org.student.guestblog.service;

import org.student.guestblog.model.Message;

import java.util.List;

public interface MessageService {
	void addMessage(Message message);
	Message findById(String id);
	void deleteMessage(Message message);
	List<Message> findAll();
}
