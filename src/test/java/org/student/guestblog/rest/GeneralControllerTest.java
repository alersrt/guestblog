package org.student.guestblog.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.student.guestblog.entity.Post;
import org.student.guestblog.repository.PostRepository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

/** Unit test for {@link GeneralController}. */
@Slf4j
@DisplayName("Test of the REST endpoints")
@Tag("unit")
class GeneralControllerTest {

  /** Mocked {@link PostRepository}. */
  private PostRepository postRepository = mock(PostRepository.class);

  /** An object of the {@link GeneralController}. */
  private GeneralController generalController = new GeneralController(postRepository, new Gson());

  /** Executes before each test. */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /** Executes after each test. */
  @AfterEach
  void tearDown() {
  }

  @DisplayName("GET '/posts/': exception")
  @Test
  void posts_exception() {
    /*------ Arranges ------*/
    when(postRepository.findAll()).thenThrow(new RuntimeException("Something went wrong"));

    /*------ Actions ------*/
    ResponseEntity<JsonObject> answer = generalController.posts();

    /*------ Asserts ------*/
    assertThat(answer.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(answer.getBody().get(Protocol.ERROR_NAME).getAsString()).isNotEmpty();
    assertThat(answer.getBody().get(Protocol.ERROR_DESCRIPTION).getAsString()).isNotEmpty();
  }

  @DisplayName("GET '/posts/': returns 204 code")
  @Test
  void posts_empty() {
    /*------ Arranges ------*/
    when(postRepository.findAll()).thenReturn(new ArrayList<>());

    /*------ Actions ------*/
    ResponseEntity<JsonObject> answer = generalController.posts();

    /*------ Asserts ------*/
    assertThat(answer.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @DisplayName("GET '/posts/': returns 200 code")
  @Test
  void posts_notEmpty() {
    /*------ Arranges ------*/
    String postId = "mockedPost0";
    List<Post> posts = new ArrayList<>();
    Post post = new Post();
    post.setId(postId);
    posts.add(post);
    when(postRepository.findAll()).thenReturn(posts);

    /*------ Actions ------*/
    ResponseEntity<JsonObject> answer = generalController.posts();

    /*------ Asserts ------*/
    assertThat(answer.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(answer.getBody().get(postId)).isNotNull();
  }
}

