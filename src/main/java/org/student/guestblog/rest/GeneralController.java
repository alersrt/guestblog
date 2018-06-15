package org.student.guestblog.rest;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.repository.MessageRepository;
import org.student.guestblog.service.MessageService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** REST-controller serves API requests. */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/api")
public class GeneralController {

  /** Instance of the {@link MessageService}. */
  private final MessageService messageService;

  /**
   * Serves "GET /users" endpoint. It is necessary for getting of the user's list.
   *
   * @return full list of users.
   */
  @GetMapping("/users")
  public ResponseEntity<JsonObject> users() {
    JsonObject answer = new JsonObject();
    return new ResponseEntity<>(answer, HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Serves "POST /users/login" endpoint and in response for login.
   *
   * @param credentials credentials of an user.
   *
   * @return information about success of this operation.
   */
  @PostMapping("/users/login")
  public ResponseEntity<JsonObject> login(@RequestBody JsonObject credentials) {
    JsonObject answer = new JsonObject();
    return new ResponseEntity<>(answer, HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Serves "POST /users/logout" endpoint and in response for user's logout.
   *
   * @param user information about user which want to logout.
   *
   * @return information about success of this operation.
   */
  @PostMapping("/users/logout")
  public ResponseEntity<JsonObject> logout(@RequestBody JsonObject user) {
    JsonObject answer = new JsonObject();
    return new ResponseEntity<>(answer, HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Serves "PUT /users/" endpoint and also used for registration.
   *
   * @param user specified information about user.
   *
   * @return information about success this operation.
   */
  @PutMapping("/users/")
  public ResponseEntity<JsonObject> userAdd(@RequestBody JsonObject user) {
    JsonObject answer = new JsonObject();
    return new ResponseEntity<>(answer, HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Serves "DELETE /users/{id}" endpoint. Removes user by its id.
   *
   * @param user request body which contains user's id.
   *
   * @return success result of this operation.
   */
  @DeleteMapping("/users/")
  public ResponseEntity<JsonObject> userDel(@RequestBody JsonObject user) {
    JsonObject answer = new JsonObject();
    return new ResponseEntity<>(answer, HttpStatus.NOT_IMPLEMENTED);
  }

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
  @GetMapping("/messages")
  public ResponseEntity<JsonObject> messages() {
    JsonObject answer = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    HttpStatus httpStatus;
    try {
      List<JsonObject> messages = messageService.getAllMessages();
      httpStatus = messages.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
      messages.forEach(jsonArray::add);
      answer.add(Protocol.MESSAGES, jsonArray);
    } catch (Exception e) {
      log.error(e.toString());
      answer.addProperty(Protocol.ERROR_NAME, e.getClass().getName());
      answer.addProperty(Protocol.ERROR_DESCRIPTION, e.getLocalizedMessage());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(answer, httpStatus);
  }

  /**
   * "PUT /messages/" endpoint.
   *
   * Gets data from JSON body of the received http-request and saves entity in the {@link
   * MessageRepository}. Returns {@link HttpStatus#OK} when all went well and {@link
   * HttpStatus#INTERNAL_SERVER_ERROR} and an error's description in otherwise.
   *
   * @param message specified message which will be added to a messages list.
   *
   * @return information about this operation success.
   *
   * @see PutMapping
   */
  @PutMapping("/messages/")
  public ResponseEntity<JsonObject> messageAdd(@RequestBody JsonObject message) {
    JsonObject answer = new JsonObject();
    HttpStatus httpStatus;
    try {
      String messageId = messageService.addMessage(message);
      answer.addProperty(Protocol.MESSAGE_ID, messageId);
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
      log.error(e.toString());
      answer.addProperty(Protocol.ERROR_NAME, e.getClass().getName());
      answer.addProperty(Protocol.ERROR_DESCRIPTION, e.getLocalizedMessage());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(answer, httpStatus);
  }

  /**
   * "DELETE /messages/{id}" endpoint.
   *
   * Removes message by its id. Gets id param from request body and remove message from {@link
   * MessageRepository}. Returns {@link HttpStatus#OK} when operation is passed well and {@link
   * HttpStatus#INTERNAL_SERVER_ERROR} with an error's description in otherwise.
   *
   * @param message request body which contains message id for removing.
   *
   * @return answer and http status.
   *
   * @see DeleteMapping
   */
  @DeleteMapping("/messages/")
  public ResponseEntity<JsonObject> messageDel(@RequestBody JsonObject message) {
    JsonObject answer = new JsonObject();
    HttpStatus httpStatus;
    try {
      messageService.deleteMessage(message);
      httpStatus = HttpStatus.OK;
    } catch (NoSuchFieldException e) {
      log.warn(e.toString());
      httpStatus = HttpStatus.BAD_REQUEST;
    } catch (NoSuchElementException e) {
      log.warn(e.toString());
      httpStatus = HttpStatus.NOT_FOUND;
    } catch (Exception e) {
      log.error(e.toString());
      answer.addProperty(Protocol.ERROR_NAME, e.getClass().getName());
      answer.addProperty(Protocol.ERROR_DESCRIPTION, e.getLocalizedMessage());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(answer, httpStatus);
  }
}
