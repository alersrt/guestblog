package org.student.guestblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.student.guestblog.dao.MessageDAO;
import org.student.guestblog.dao.UserDAO;
import org.student.guestblog.model.Message;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDAO messageDAO;

	@Autowired
	private UserDAO userDAO;

	@Override
	public Message addMessage(Message message) {
		message.setTitle(HtmlUtils.htmlEscape(message.getTitle()));
		message.setBody(HtmlUtils.htmlEscape(message.getBody()));
		message.setUser(userDAO.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

		return messageDAO.save(message);
	}

	@Override
	public Message findById(String id) {
		return messageDAO.findOne(id);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or (fullyAuthenticated and #message.ownerName.equals(authentication.name))")
	public void deleteMessage(@P("message") Message message) {
		messageDAO.delete(message);
	}

	@Override
	public List<Message> findAll() {
		return messageDAO.findAll();
	}
}
