package org.student.guestblog.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
public class Message {
	@Id
	private String id;

	private LocalDateTime localDateTime;
	private String title;
	private String txtOfMessage;

	public Message() {
	}

	public Message(String title, String txtOfMessage) {
		this.localDateTime = LocalDateTime.now();
		this.title = title;
		this.txtOfMessage = txtOfMessage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxtOfMessage() {
		return txtOfMessage;
	}

	public void setTxtOfMessage(String txtOfMessage) {
		this.txtOfMessage = txtOfMessage;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id='" + id + '\'' +
				", localDateTime=" + localDateTime +
				", title='" + title + '\'' +
				", txtOfMessage='" + txtOfMessage + '\'' +
				'}';
	}
}
