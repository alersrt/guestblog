package org.student.guestblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.student.guestblog.dao.MessageDAO;
import org.student.guestblog.dao.UserDAO;
import org.student.guestblog.model.Message;
import org.student.guestblog.model.User;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDAO messageDAO;

	@Autowired
	private UserDAO userDAO;

	@Override
	public void addMessage(Message message, User user) {
		message.setTitle(HtmlUtils.htmlEscape(message.getTitle()));
		message.setBody(HtmlUtils.htmlEscape(message.getBody()));
		message.setUser(user);

		//Set<Message> messages = user.getMessages();
		//messages.add(message);
		//user.setMessages(messages);

		messageDAO.save(message);
		//userDAO.save(user);
	}

	@Override
	public Message findById(String id) {
		return messageDAO.findOne(id);
	}

	@Override
	public void deleteById(String id) {
		messageDAO.delete(messageDAO.findOne(id));
	}

	@Override
	public List<Message> findAll() {
		return messageDAO.findAll();
	}
}
