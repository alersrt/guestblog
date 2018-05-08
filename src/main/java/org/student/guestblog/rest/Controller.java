package org.student.guestblog.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.service.PostService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

/** REST-controller serves API requests. */
@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {

  /** Bean of the {@link PostService}. */
  @Autowired private PostService postService;

  /** Bean of the {@link Gson}. */
  @Autowired private Gson gson;

  /**
   * Serves "GET /users" endpoint. It is necessary for getting of the user's list.
   *
   * @return full list of users.
   */
  @GetMapping("/users")
  public ResponseEntity<JsonObject> users() {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "GET /posts" endpoint. Returns lists of posts.
   *
   * @return full list of posts.
   */
  @GetMapping("/posts")
  public ResponseEntity<JsonArray> posts() {
    JsonArray answer = new JsonArray();
    postService.getAllPosts().forEach(p -> answer.add(gson.toJson(p)));
    return new ResponseEntity<>(answer, HttpStatus.OK);
  }

  /**
   * Serves "POST /users/login" endpoint and in response for login.
   *
   * @param credentials credentials of an user.
   * @return information about success of this operation.
   */
  @PostMapping("/users/login")
  public ResponseEntity<JsonObject> login(@RequestBody JsonObject credentials) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "POST /users/logout" endpoint and in response for user's logout.
   *
   * @param user information about user which want to logout.
   * @return information about success of this operation.
   */
  @PostMapping("/users/logout")
  public ResponseEntity<JsonObject> logout(@RequestBody JsonObject user) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "POST /users/add" endpoint and also used for registration.
   *
   * @param user specified information about user.
   * @return information about success this operation.
   */
  @PostMapping("/users/add")
  public ResponseEntity<JsonObject> userAdd(@RequestBody JsonObject user) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "POST /users/del" endpoint. Removes user by its id.
   *
   * @param user contains id of the removed user.
   * @return success result of this operation.
   */
  @PostMapping("/users/del")
  public ResponseEntity<JsonObject> userDel(@RequestBody JsonObject user) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "POST /posts/add" endpoint. Adds new post on board.
   *
   * @param post specified post which will be added to a posts list.
   * @return information about this operation success.
   */
  @PostMapping("/posts/add")
  public ResponseEntity<JsonObject> postAdd(@RequestBody JsonObject post) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "POST /posts/del" endpoint. Removes post by its id.
   *
   * @param post contains id of the post which need to remove.
   * @return succes of removing operation.
   */
  @PostMapping("/posts/del")
  public ResponseEntity<JsonObject> postDel(@RequestBody JsonObject post) {
    throw new UnsupportedOperationException();
  }
}
