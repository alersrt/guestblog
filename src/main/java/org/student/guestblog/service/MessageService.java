package org.student.guestblog.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.student.guestblog.entity.Message;
import org.student.guestblog.repository.MessageRepository;
import org.student.guestblog.rest.Protocol;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.gridfs.model.GridFSFile;

import lombok.RequiredArgsConstructor;

/**
 * Service manages of messages. Here is implemented such features as adding, deleting, editing,
 * getting of messages.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MessageService {

  /** Instance of the {@link Gson}. */
  private final Gson gson;

  /** Instance of the {@link MessageRepository} object. */
  private final MessageRepository messageRepository;

  /** Instance of the {@link GridFsOperations} for managing of the messages' attachments. */
  private final GridFsOperations gridFsOperations;

  /**
   * Add message to repository and returns id of the added message.
   *
   * @param jsonMessage source of the new message.
   *
   * @return String id of the new stored message.
   */
  public String addMessage(JsonObject jsonMessage) {
    Message result = new Message();

    result.setTimestamp(LocalDateTime.now());
    if (!jsonMessage.get(Protocol.MESSAGE_TITLE).isJsonNull()) {
      result.setTitle(jsonMessage.get(Protocol.MESSAGE_TITLE).getAsString());
    }
    if (!jsonMessage.get(Protocol.MESSAGE_TEXT).isJsonNull()) {
      result.setText(jsonMessage.get(Protocol.MESSAGE_TEXT).getAsString());
    }
    if (!jsonMessage.get(Protocol.MESSAGE_FILE).isJsonNull()) {
      String filename = UUID.randomUUID().toString();
      String base64File = jsonMessage.get(Protocol.MESSAGE_FILE).getAsString();
      String mime = base64File.substring(base64File.indexOf(":") + 1, base64File.indexOf(";"));
      ObjectId file = gridFsOperations.store(
        new ByteArrayInputStream(Base64.getDecoder().decode(base64File.split(",")[1])),
        filename,
        mime
      );
      result.setFile(file);
    }

    return messageRepository.save(result).getId();
  }

  /**
   * Removes {@link Message} from database by it's id.
   *
   * @param jsonMessage json message which contains id of the deleted message.
   */
  public void deleteMessage(JsonObject jsonMessage) {
    boolean result = false;

    if (!jsonMessage.get(Protocol.MESSAGE_ID).isJsonNull()) {
      Optional<Message> removedMessage = messageRepository
        .findById(jsonMessage.get(Protocol.MESSAGE_ID).getAsString());

      gridFsOperations
        .delete(Query.query(Criteria.where("_id").is(removedMessage.get().getFile().toString())));
      messageRepository.delete(removedMessage.get());
    }
  }

  /**
   * Returns list of all messages in JSON format.
   *
   * @return {@link List} of the {@link JsonObject}.
   */
  public List<JsonObject> getAllMessages() {
    List<JsonObject> result = new ArrayList<>();
    messageRepository.findAll().forEach(message -> {
      JsonObject jsonMessage = new JsonObject();

      jsonMessage.addProperty(Protocol.MESSAGE_ID, message.getId());
      jsonMessage
        .add(Protocol.MESSAGE_TIMESTAMP, gson.toJsonTree(message.getTimestamp()).getAsJsonObject());
      jsonMessage.addProperty(Protocol.MESSAGE_IS_EDITED, message.isEdited());

      if (!message.getTitle().isEmpty()) {
        jsonMessage.addProperty(Protocol.MESSAGE_TITLE, message.getTitle());
      }
      if (!message.getText().isEmpty()) {
        jsonMessage.addProperty(Protocol.MESSAGE_TEXT, message.getText());
      }
      if (message.getFile() != null) {

        GridFSFile file = gridFsOperations
          .findOne(Query.query(Criteria.where("_id").is(message.getFile().toString())));
        GridFsResource resource = gridFsOperations.getResource(file.getFilename());
        String mime = resource.getContentType();
        byte[] bytes = new byte[0];
        try {
          bytes = ByteStreams.toByteArray(resource.getInputStream());
        } catch (IOException e) {
          e.printStackTrace();
        }
        jsonMessage.addProperty(Protocol.MESSAGE_FILE,
          "data:" + mime + ";base64," + Base64.getEncoder().encodeToString(bytes));
      }

      result.add(jsonMessage);
    });

    return result;
  }
}
