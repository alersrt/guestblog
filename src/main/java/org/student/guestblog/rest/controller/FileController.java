package org.student.guestblog.rest.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.student.guestblog.service.FileService;

@Controller("/api/file")
@Secured(SecurityRule.IS_ANONYMOUS)
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Get("/{filename}")
    public HttpResponse<byte[]> getFile(@PathVariable String filename) {
        return fileService.getResource(filename)
            .map(f -> HttpResponse.ok(f.getContent()).contentType(f.getMime()))
            .orElseGet(HttpResponse::noContent);
    }
}
