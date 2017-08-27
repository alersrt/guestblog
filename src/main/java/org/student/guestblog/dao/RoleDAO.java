package org.student.guestblog.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.Role;

@Repository
public interface RoleDAO extends MongoRepository<Role, String> {
	Role findByRolename(String rolename);
}
