package org.student.guestblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.guestblog.dao.MessageDAO;
import org.student.guestblog.model.Message;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDAO messageDAO;

	@Override
	public void addMessage(Message message) {
		messageDAO.save(message);
	}
}
