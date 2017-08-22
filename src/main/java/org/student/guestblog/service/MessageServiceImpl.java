package org.student.guestblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.guestblog.dao.MessageDAO;
import org.student.guestblog.model.Message;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDAO messageDAO;

	@Override
	public void save(Message message) {
		messageDAO.save(message);
	}

	@Override
	public List<Message> findAll() {
		return messageDAO.findAll();
	}
}
