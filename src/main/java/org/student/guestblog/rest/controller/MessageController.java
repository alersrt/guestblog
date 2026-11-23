package org.student.guestblog.rest.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.service.FileService;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.storage.entity.MessageEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller("/api/message")
@Produces(MediaType.APPLICATION_JSON)
@Secured(SecurityRule.IS_ANONYMOUS)
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<MessageResponse> addMessage(Authentication authentication,
                                                    @Part("metadata") MessageRequest metadata,
                                                    @Part("file") Optional<CompletedFileUpload> file)
        throws IOException {
        var storedFile = file.map(fileService::save);
        MessageEntity savedMessageEntity = null;
        try {
            UUID authorId = null;
            if (authentication != null) {
                Object id = authentication.getAttributes().get("id");
                if (id != null) {
                    authorId = UUID.fromString(id.toString());
                }
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
