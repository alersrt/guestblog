package org.student.guestblog.usecases;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.multipart.MultipartBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.student.guestblog.AbstractIntegrationTest;
import org.student.guestblog.rest.dto.message.MessageRequest;
import org.student.guestblog.rest.dto.message.MessageResponse;
import org.student.guestblog.rest.dto.user.UserResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Check working of the message related stuff.
 */
@DisplayName("Message infrastructure test")
public class MessageEntityTest extends AbstractIntegrationTest {

    @DisplayName("new message: authenticated")
    @Test
    void createMessage_withAuth() throws IOException {
        /*------ Arranges ------*/
        var authCookie = getUserAuthorization("user@test.dev", "password");
        var messageMetadata = new MessageRequest("TEST", "Lorem ipsum...");

        /*------ Actions ------*/

        // Get current user
        var currentUser = client.toBlocking().exchange(
            HttpRequest.GET("/api/account/me")
                .header("Cookie", authCookie),
            UserResponse.class
        ).body();

        // Create the new message
        byte[] fileBytes;
        try (var testFile = getClass().getResourceAsStream("/test.png")) {
            assertThat(testFile).isNotNull();
            fileBytes = testFile.readAllBytes();
        }
        var body = MultipartBody.builder()
            .addPart("metadata", "metadata.json", MediaType.APPLICATION_JSON_TYPE,
                objectMapper.writeValueAsBytes(messageMetadata))
            .addPart("file", "test.png", MediaType.of("image/png"), fileBytes)
            .build();
        var createdMessage = client.toBlocking().exchange(
            HttpRequest.POST("/api/message", body)
                .contentType(MediaType.MULTIPART_FORM_DATA_TYPE)
                .header("Cookie", authCookie),
            MessageResponse.class
        ).body();

        // Get the created message
        var gotMessage = client.toBlocking().exchange(
            HttpRequest.GET("/api/message/" + createdMessage.id())
                .header("Cookie", authCookie),
            MessageResponse.class
        ).body();

        /*------ Asserts ------*/
        assertAll(
            () -> assertThat(currentUser.id()).isNotEmpty(),
            () -> assertThat(createdMessage.id()).isNotNull(),
            () -> assertThat(createdMessage.authorId()).isEqualTo(currentUser.id().get()),
            () -> assertThat(gotMessage).isEqualTo(createdMessage)
        );
    }
}
