package org.student.guestblog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.User;

@Repository
public interface UserDAO extends MongoRepository<User, String> {
	User findByUsername(String username);
}
