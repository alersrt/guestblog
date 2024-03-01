package org.student.guestblog.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.student.guestblog.security.User;
import org.student.guestblog.data.entity.MessageEntity;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.service.AccountService;
import org.student.guestblog.service.FileService;
import org.student.guestblog.service.MessageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;
    private final AccountService accountService;
    private final FileService fileService;

    public MessageController(
        MessageService messageService,
        AccountService accountService,
        FileService fileService
    ) {
        this.messageService = messageService;
        this.accountService = accountService;
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages() {
        var messages =
            messageService.getAllMessages().stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
        var httpStatus = !messages.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(httpStatus).body(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable UUID id) {
        var message = messageService.getMessage(id);
        return message.map(MessageResponse::new)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addMessage(
        Authentication authentication,
        @RequestPart("metadata") MessageRequest metadata,
        @RequestPart("file") Optional<MultipartFile> file
    ) throws IOException {
        var storedFile = file.map(fileService::save);
        MessageEntity savedMessageEntity = null;
        try {
            UUID authorId = null;
            if (authentication != null && authentication.isAuthenticated()) {
                var user = (User) authentication.getPrincipal();
                authorId = user.id();
            }
            savedMessageEntity = messageService.addMessage(metadata.title(), metadata.text(), storedFile.orElse(null), authorId);
        } catch (Exception e) {
            storedFile.ifPresent(fileService::delete);
            throw e;
        }
        return ResponseEntity.ok(new MessageResponse(savedMessageEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }
}
