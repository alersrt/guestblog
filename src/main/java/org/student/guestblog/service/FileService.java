package org.student.guestblog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.student.guestblog.model.File;
import org.student.guestblog.repository.FileRepository;
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
            .map(file -> new FileResource(file.getBlob(), file.getFilename(), file.getMime()));
    }

    public File save(MultipartFile file) {
        try {
            String extension = MimeTypesAndExtensions.getDefaultExt(file.getContentType());
            var filename = UUID.randomUUID() + "." + extension;
            return fileRepository.save(
                new File()
                    .setFilename(filename)
                    .setMime(file.getContentType())
                    .setBlob(file.getBytes())
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
    public void delete(long id) {
        fileRepository.deleteById(id);
    }

    public void delete(File model) {
        fileRepository.delete(model);
    }
}
