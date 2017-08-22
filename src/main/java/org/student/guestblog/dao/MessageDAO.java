package org.student.guestblog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.student.guestblog.model.Message;

public interface MessageDAO extends MongoRepository<Message, String> {
	
}
