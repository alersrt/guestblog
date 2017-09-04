package org.student.guestblog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.Message;

import java.util.List;

@Repository
public interface MessageDAO extends MongoRepository<Message, String> {
	List<Message> findByTitle(String title);
	List<Message> findByOwnerName(String ownerName);
}
