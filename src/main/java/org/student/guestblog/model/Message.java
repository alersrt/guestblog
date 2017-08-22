package org.student.guestblog.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "messages")
public class Message {
	@Id
	private String id;

	private LocalDateTime timestamp;

	private String title;

	private String body;

	public Message() {
		this.timestamp = LocalDateTime.now();
	}

	public Message(String title, String body) {
		this.timestamp = LocalDateTime.now();
		this.title = title;
		this.body = body;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Message message = (Message) o;

		if (!id.equals(message.id)) return false;
		if (!timestamp.equals(message.timestamp)) return false;
		if (!title.equals(message.title)) return false;
		return body.equals(message.body);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + timestamp.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + body.hashCode();
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
