package org.student.guestblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.student.guestblog.model.Passport;

public interface PassportRepository extends JpaRepository<Passport, Long> {

}
