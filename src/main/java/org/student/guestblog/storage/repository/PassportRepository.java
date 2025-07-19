package org.student.guestblog.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.guestblog.storage.entity.PassportEntity;

import java.util.UUID;

public interface PassportRepository extends JpaRepository<PassportEntity, UUID> {

}
