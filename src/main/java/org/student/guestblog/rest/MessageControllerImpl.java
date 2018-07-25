package org.student.guestblog.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.student.guestblog.DTO.MessageDto;
import org.student.guestblog.profiling.Profiling;
import org.student.guestblog.service.MessageService;

@Profiling
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class MessageControllerImpl implements MessageController {

  /** Instance of the {@link MessageService}. */
  private final MessageService messageService;

  @Override
  public ResponseEntity<List<MessageDto>> messages() {
    List<MessageDto> messages = null;
    HttpStatus httpStatus;
    try {
      messages = messageService.getAllMessages();
      httpStatus = messages.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    } catch (Exception e) {
      log.error(e.toString());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(messages, httpStatus);
  }

  @Override
  public ResponseEntity<String> messageAdd(MessageDto messageDto) {
    String messageId = null;
    HttpStatus httpStatus;
    try {
      messageId = messageService.addMessage(messageDto).getId();
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
      log.error(e.toString());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(messageId, httpStatus);
  }

  @Override
  public ResponseEntity messageDel(MessageDto messageDto) {
    HttpStatus httpStatus;
    try {
      messageService.deleteMessage(messageDto);
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
      log.error(e.toString());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(httpStatus);
  }
}
