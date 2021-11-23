package org.student.guestblog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;

/**
 * Check working of the message related stuff.
 */
@DisplayName("Message infrastructure test")
public class MessageTest extends AbstractIntegrationTest {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Value("classpath:test.png")
  private Resource testFile;

  @DisplayName("new message: authenticated")
  @Test
  void createMessage_withAuth() throws Exception {
    /*------ Arranges ------*/
    var authCookie = getUserAuthorization("user", "password");
    var messageMetadata = new MessageRequest("TEST", "Lorem ipsum...");

    /*------ Actions ------*/

    // Get current user
    var getCurrentUserAction = mockMvc.perform(
        get("/api/account/me")
            .cookie(authCookie)
    );
    var currentUser = objectMapper.readValue(
        getCurrentUserAction.andReturn().getResponse().getContentAsString(),
        UserResponse.class
    );
    authCookie = prolongAuthCookie(getCurrentUserAction.andReturn().getResponse());

    // Create the new message
    MockMultipartFile file = new MockMultipartFile(
        "file", testFile.getFilename(), MediaType.IMAGE_PNG_VALUE, testFile.getInputStream().readAllBytes()
    );
    MockMultipartFile metadata = new MockMultipartFile(
        "metadata", "metadata.json", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(messageMetadata)
    );
    var postMessageAction = mockMvc.perform(
        multipart("/api/message")
            .file(file)
            .file(metadata)
            .cookie(authCookie)
    );
    var createdMessage = objectMapper.readValue(
        postMessageAction.andReturn().getResponse().getContentAsString(),
        MessageResponse.class
    );
    authCookie = prolongAuthCookie(postMessageAction.andReturn().getResponse());

    // Get all messages
    var getMessageAction = mockMvc.perform(
        get(String.format("/api/message/%s", createdMessage.id().toString()))
            .cookie(authCookie)
    );
    var gotMessage = objectMapper.readValue(
        getMessageAction.andReturn().getResponse().getContentAsString(),
        MessageResponse.class
    );

    /*------ Asserts ------*/
    getCurrentUserAction.andExpect(status().isOk());
    postMessageAction.andExpect(status().isOk());
    getMessageAction.andExpect(status().isOk());
    assertAll(
        () -> assertThat(currentUser.id()).isNotEmpty(),
        () -> assertThat(createdMessage.id()).isNotNull(),
        () -> assertThat(createdMessage.authorId()).isEqualTo(currentUser.id().get()),
        () -> assertThat(gotMessage).isEqualTo(createdMessage)
    );
  }
}
