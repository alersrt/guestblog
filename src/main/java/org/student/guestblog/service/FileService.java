package org.student.guestblog.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.student.guestblog.data.entity.FileEntity;
import org.student.guestblog.model.internal.FileResource;

import java.util.Optional;
import java.util.UUID;


public interface FileService {

    /**
     * Returns file as {@link Resource}.
     *
     * @param filename name of the file.
     * 
     * @return Resource.
     */
    Optional<FileResource> getResource(String filename);

    FileEntity save(MultipartFile file);

    void delete(UUID id);

    void delete(FileEntity model);
}
