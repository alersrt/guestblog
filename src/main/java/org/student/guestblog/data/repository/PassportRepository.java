package org.student.guestblog.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.guestblog.data.entity.PassportEntity;

import java.util.UUID;

public interface PassportRepository extends JpaRepository<PassportEntity, UUID> {

}
