package org.student.guestblog.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.DTO.MessageDto;
import org.student.guestblog.repository.MessageRepository;

/**
 * REST-controller serves "/api/messages/*" endpoints.
 */
@RestController
@RequestMapping("/api/messages")
public interface MessageController {

  /**
   * "GET /messages" endpoint.
   *
   * Returns lists of messages. If the list is empty then returns {@link HttpStatus#NO_CONTENT} code
   * and {@link HttpStatus#OK} in otherwise. If something went wrong then {@link
   * HttpStatus#INTERNAL_SERVER_ERROR} and error description will be returned.
   *
   * @return list of the messages and http status.
   *
   * @see GetMapping
   */
  @GetMapping("/")
  ResponseEntity<List<MessageDto>> messages();

  /**
   * "PUT /messages/" endpoint.
   *
   * Gets data from JSON body of the received http-request and saves entity in the {@link
   * MessageRepository}. Returns {@link HttpStatus#OK} when all went well and {@link
   * HttpStatus#INTERNAL_SERVER_ERROR} in otherwise.
   *
   * @param messageDto specified message which will be added to a messages list.
   *
   * @return information about this operation success.
   *
   * @see PutMapping
   */
  @PutMapping("/")
  ResponseEntity<String> messageAdd(@RequestBody MessageDto messageDto);

  /**
   * "DELETE /messages/{id}" endpoint.
   *
   * Removes message by its id. Gets id param from request body and remove message from {@link
   * MessageRepository}. Returns {@link HttpStatus#OK} when operation is passed well and {@link
   * HttpStatus#INTERNAL_SERVER_ERROR} in otherwise.
   *
   * @param messageDto request body which contains message id for removing.
   *
   * @return answer and http status.
   *
   * @see DeleteMapping
   */
  @DeleteMapping("/")
  ResponseEntity messageDel(@RequestBody MessageDto messageDto);
}
