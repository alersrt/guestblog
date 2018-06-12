package org.student.guestblog.rest;

import java.time.LocalDateTime;
import java.util.List;

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
import org.student.guestblog.entity.Message;
import org.student.guestblog.entity.User;
import org.student.guestblog.repository.MessageRepository;
import org.student.guestblog.repository.UserRepository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** REST-controller serves API requests. */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/api")
public class GeneralController {

  /** The {@link MessageRepository} single. */
  private final MessageRepository messageRepository;

  /** The {@link UserRepository} single. */
  private final UserRepository userRepository;

  /** The {@link Gson} single object. */
  private final Gson gson;

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
    List<Message> messages;
    HttpStatus httpStatus;
    try {
      messages = messageRepository.findAll();
      httpStatus = messages.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
      messages.forEach(p -> jsonArray.add(gson.toJsonTree(p)));
      answer.add(Protocol.MESSAGES, jsonArray);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage());
      answer.addProperty(Protocol.ERROR_NAME, e.getClass().getName());
      answer.addProperty(Protocol.ERROR_DESCRIPTION, e.getLocalizedMessage());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(answer, httpStatus);
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
    User newUser = User.builder()
      .username(user.get(Protocol.USER_USERNAME).getAsString())
      .password(user.get(Protocol.USER_PASSWORD).getAsString())
      .email(user.get(Protocol.USER_EMAIL).getAsString())
      .build();

    JsonObject answer = new JsonObject();
    HttpStatus httpStatus;
    try {
      answer.addProperty(Protocol.USER_ID, userRepository.save(newUser).getId());
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
      answer.addProperty(Protocol.ERROR_NAME, e.getClass().getName());
      answer.addProperty(Protocol.ERROR_DESCRIPTION, e.getLocalizedMessage());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(answer, httpStatus);
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
    HttpStatus httpStatus;
    try {
      messageRepository.deleteById(user.get(Protocol.USER_ID).getAsString());
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
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
    Message addedMessage = Message.builder()
      .title(message.get(Protocol.MESSAGE_TITLE).getAsString())
      .text(message.get(Protocol.MESSAGE_TEXT).getAsString())
      .timestamp(LocalDateTime.now())
      .build();

    JsonObject answer = new JsonObject();
    HttpStatus httpStatus;
    try {
      addedMessage = messageRepository.save(addedMessage);
      answer.addProperty(Protocol.MESSAGE_ID, addedMessage.getId());
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
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
      messageRepository.deleteById(message.get(Protocol.MESSAGE_ID).getAsString());
      httpStatus = HttpStatus.OK;
    } catch (Exception e) {
      answer.addProperty(Protocol.ERROR_NAME, e.getClass().getName());
      answer.addProperty(Protocol.ERROR_DESCRIPTION, e.getLocalizedMessage());
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(answer, httpStatus);
  }
}
