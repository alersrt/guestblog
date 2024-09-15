package org.student.guestblog.usecases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.student.guestblog.AbstractIntegrationTest;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.rest.dto.user.UserResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Check working of the message related stuff.
 */
@DisplayName("Message infrastructure test")
public class MessageEntityTest extends AbstractIntegrationTest {

    @Value("classpath:test.png")
    private Resource testFile;

    @DisplayName("new message: authenticated")
    @Test
    void createMessage_withAuth() throws Exception {
        /*------ Arranges ------*/
        var authCookie = getUserAuthorization("user@test.dev", "password");
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
