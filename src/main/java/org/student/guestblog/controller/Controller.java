package org.student.guestblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

/** REST-controller serves API requests. */
@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {

  /**
   * Serves "/user/login" endpoint and in responsible for login.
   *
   * @param credentials credentials of an user.
   * @return information about success of this operation.
   */
  @PostMapping("/user/login")
  public ResponseEntity<JsonObject> login(@RequestBody JsonObject credentials) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "/user/logout" endpoint and in responsible for user's logout.
   *
   * @return information about success of this operation.
   */
  @GetMapping("/user/logout")
  public ResponseEntity<JsonObject> logout() {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/user/add")
  public ResponseEntity<JsonObject> userAdd(@RequestBody JsonObject user) {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/user/del")
  public ResponseEntity<JsonObject> userDel(@RequestBody JsonObject user) {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/post/add")
  public ResponseEntity<JsonObject> postAdd(@RequestBody JsonObject post) {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/post/del")
  public ResponseEntity<JsonObject> postDel(@RequestBody JsonObject post) {
    throw new UnsupportedOperationException();
  }
}
