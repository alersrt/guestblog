package org.student.guestblog.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.student.guestblog.entity.Post;
import org.student.guestblog.service.PostService;

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

  /** The {@link PostService} single. */
  private final PostService postService;

  /** The {@link Gson} single object. */
  private final Gson gson;

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
   * Serves "GET /posts" endpoint. Returns lists of posts but if the list is empty then returns
   * "204" status code and "200" in otherwise.
   *
   * @return full list of posts.
   */
  @GetMapping("/posts")
  public ResponseEntity<JsonArray> posts() {
    JsonArray answer = new JsonArray();
    List<Post> posts = postService.getAllPosts();
    HttpStatus httpStatus = posts.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    posts.forEach(p -> answer.add(gson.toJson(p)));
    return new ResponseEntity<>(answer, httpStatus);
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
   * Serves "PUT /users/" endpoint and also used for registration.
   *
   * @param user specified information about user.
   * @return information about success this operation.
   */
  @PutMapping("/users/")
  public ResponseEntity<JsonObject> userAdd(@RequestBody JsonObject user) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "DELETE /users/{id}" endpoint. Removes user by its id.
   *
   * @param id user's id.
   * @return success result of this operation.
   */
  @DeleteMapping("/users/{id}")
  public ResponseEntity<JsonObject> userDel(@PathVariable String id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "PUT /posts/" endpoint. Adds new post on board.
   *
   * @param post specified post which will be added to a posts list.
   * @return information about this operation success.
   */
  @PutMapping("/posts/")
  public ResponseEntity<JsonObject> postAdd(@RequestBody JsonObject post) {
    throw new UnsupportedOperationException();
  }

  /**
   * Serves "DELETE /posts/{id}" endpoint. Removes post by its id.
   *
   * @param id id of the removed post.
   * @return success of removing operation.
   */
  @DeleteMapping("/posts/{id}")
  public ResponseEntity<JsonObject> postDel(@PathVariable String id) {
    throw new UnsupportedOperationException();
  }
}
