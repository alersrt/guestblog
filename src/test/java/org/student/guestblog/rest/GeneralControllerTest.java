package org.student.guestblog.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.student.guestblog.repository.PostRepository;

import com.google.gson.JsonObject;

/** Unit test for {@link GeneralController}. */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("Test of the REST endpoints")
@Tag("unit")
public class GeneralControllerTest {

  /** Rest client for the testing. */
  @Autowired
  private TestRestTemplate restTemplate;

  /** Mocked {@link PostRepository}. */
  @MockBean
  private PostRepository postRepository;

  /** Executes before each test. */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /** Executes after each test. */
  @AfterEach
  void tearDown() {}

  @DisplayName("GET '/posts/': exception")
  @Test
  void posts_exception() {
    /*------ Arranges ------*/
    when(postRepository.findAll()).thenThrow(new RuntimeException("Something went wrong"));

    /*------ Actions ------*/
    ResponseEntity<JsonObject> answer = restTemplate.getForEntity("/api/posts", JsonObject.class);

    /*------ Asserts ------*/
    assertThat(answer.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(answer.getBody().get(Protocol.ERROR_NAME).getAsString()).isNotEmpty();
    assertThat(answer.getBody().get(Protocol.ERROR_DESCRIPTION).getAsString()).isNotEmpty();
  }
}

