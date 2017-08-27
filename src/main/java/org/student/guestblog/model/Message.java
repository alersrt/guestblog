package org.student.guestblog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;

@Document(collection = "messages")
public class Message {
	@Id
	private String id;

	@NotNull
	@Field("timestamp")
	private LocalDateTime timestamp;

	@NotBlank
	@Size(max = 100)
	@Field("title")
	private String title;

	@NotBlank
	@Size(max = 1024)
	@Field("body")
	private String body;

	@NotNull
	@Size(max = 16777216)
	@Field("image")
	private byte[] image;

	public String getId() {
		return id;
	}

	public Message() {
		this.timestamp = LocalDateTime.now();
	}

	public Message(@NotBlank @Size(max = 100) String title, @NotBlank @Size(max = 1024) String body, @NotNull @Size(max = 16777216) byte[] image) {
		this.timestamp = LocalDateTime.now();
		this.title = title;
		this.body = body;
		this.image = image;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message message = (Message) o;

		if (!timestamp.equals(message.timestamp)) return false;
		if (!title.equals(message.title)) return false;
		if (!body.equals(message.body)) return false;
		return Arrays.equals(image, message.image);
	}

	@Override
	public int hashCode() {
		int result = timestamp.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + body.hashCode();
		result = 31 * result + Arrays.hashCode(image);
		return result;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id='" + id + '\'' +
				", timestamp=" + timestamp +
				", title='" + title + '\'' +
				", body='" + body + '\'' +
				'}';
	}
}
