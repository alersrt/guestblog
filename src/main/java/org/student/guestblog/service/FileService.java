package org.student.guestblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.student.guestblog.data.entity.FileEntity;
import org.student.guestblog.data.repository.FileRepository;
import org.student.guestblog.service.internal.FileResource;
import org.student.guestblog.util.MimeTypesAndExtensions;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Returns file as {@link Resource}.
     *
     * @param filename name of the file.
     * @return Resource.
     */
    public Optional<FileResource> getResource(String filename) {
        return fileRepository
            .findByFilename(filename)
            .map(fileEntity -> new FileResource(fileEntity.getBlob(), fileEntity.getFilename(), fileEntity.getMime()));
    }

    public FileEntity save(MultipartFile file) {
        try {
            String extension = MimeTypesAndExtensions.getDefaultExt(file.getContentType());
            var filename = UUID.randomUUID() + "." + extension;
            return fileRepository.save(
                FileEntity.builder()
                    .id(UUID.randomUUID())
                    .filename(filename)
                    .mime(file.getContentType())
                    .blob(file.getBytes())
                    .build()
            );
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Removes file by its id.
     *
     * @param id file's identifier.
     */
    public void delete(UUID id) {
        fileRepository.deleteById(id);
    }

    public void delete(FileEntity model) {
        fileRepository.delete(model);
    }
}
