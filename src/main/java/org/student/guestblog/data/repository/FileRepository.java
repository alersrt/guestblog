package org.student.guestblog.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.student.guestblog.data.entity.FileEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

    Optional<FileEntity> findByFilename(String filename);
}
