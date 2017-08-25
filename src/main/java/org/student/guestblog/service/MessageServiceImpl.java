package org.student.guestblog.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.student.guestblog.dao.MessageDAO;
import org.student.guestblog.model.Message;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDAO messageDAO;

	@Override
	public void save(Message message) {
		message.setTitle(HtmlUtils.htmlEscape(message.getTitle()));
		message.setBody(HtmlUtils.htmlEscape(message.getBody()));
		messageDAO.save(message);
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
