package org.student.guestblog.rest.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import org.student.guestblog.domain.internal.FileResource;
import org.student.guestblog.service.FileService;

@Controller("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Get("/{filename}")
    public HttpResponse<FileResource> getFile(@PathVariable String filename) {
        return fileService.getResource(filename)
            .map(f -> HttpResponse.ok().contentType(MediaType.of(f.getMime())).body(f))
            .orElseGet(HttpResponse::noContent);
    }
}
