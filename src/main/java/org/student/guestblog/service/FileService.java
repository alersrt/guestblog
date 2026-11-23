package org.student.guestblog.service;

import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import org.student.guestblog.domain.internal.FileResource;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ErrorCode;
import org.student.guestblog.storage.entity.FileEntity;
import org.student.guestblog.storage.repository.FileRepository;
import org.student.guestblog.util.MimeTypesAndExtensions;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Singleton
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Returns file as {@link FileResource}.
     *
     * @param filename name of the file.
     * @return Resource.
     */
    public Optional<FileResource> getResource(String filename) {
        return fileRepository
            .findByFilename(filename)
            .map(fileEntity -> new FileResource(fileEntity.getBlob(), fileEntity.getFilename(),
                fileEntity.getMime()));
    }

    public FileEntity save(CompletedFileUpload file) {
        try {
            String mime = file.getContentType()
                .map(MediaType::toString)
                .orElse(MimeTypesAndExtensions.MIME_APPLICATION_OCTET_STREAM);
            String extension = MimeTypesAndExtensions.getDefaultExt(mime);
            var filename = UUID.randomUUID() + "." + extension;
            return fileRepository.save(
                FileEntity.builder()
                    .id(UUID.randomUUID())
                    .filename(filename)
                    .mime(mime)
                    .blob(file.getBytes())
                    .build()
            );
        } catch (IOException e) {
            throw new ApplicationException(ErrorCode.GENERIC_ERROR_CODE);
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
