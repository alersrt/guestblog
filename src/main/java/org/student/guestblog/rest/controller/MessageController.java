package org.student.guestblog.rest.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.authentication.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.security.User;
import org.student.guestblog.service.FileService;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.storage.entity.MessageEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller("/api/message")
public class MessageController {

    private final MessageService messageService;
    private final FileService fileService;

    public MessageController(MessageService messageService,
                             FileService fileService) {
        this.messageService = messageService;
        this.fileService = fileService;
    }

    @Get
    public HttpResponse<List<MessageResponse>> getMessages() {
        var messages = messageService.getAllMessages().stream()
            .map(MessageResponse::new)
            .collect(Collectors.toList());
        if (messages.isEmpty()) {
            return HttpResponse.noContent();
        } else {
            return HttpResponse.ok(messages);
        }
    }

    @Get("/{id}")
    public HttpResponse<MessageResponse> getMessage(@PathVariable UUID id) {
        var message = messageService.getMessage(id);
        return message.map(MessageResponse::new)
            .map(HttpResponse::ok)
            .orElse(HttpResponse.noContent());
    }

    @Post
    public HttpResponse<MessageResponse> addMessage(Authentication authentication,
                                                    @Part("metadata") MessageRequest metadata,
                                                    @Part("file") Optional<CompletedFileUpload> file)
        throws IOException {
        var storedFile = file.map(fileService::save);
        MessageEntity savedMessageEntity = null;
        try {
            UUID authorId = null;
            if (authentication != null && authentication.isAuthenticated()) {
                var user = (User) authentication.getPrincipal();
                authorId = user.id();
            }
            savedMessageEntity = messageService.addMessage(
                metadata.title(),
                metadata.text(),
                storedFile.orElse(null),
                authorId
            );
        } catch (Exception e) {
            storedFile.ifPresent(fileService::delete);
            throw e;
        }
        return HttpResponse.ok(new MessageResponse(savedMessageEntity));
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        return HttpResponse.ok();
    }
}
