package org.student.guestblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}
