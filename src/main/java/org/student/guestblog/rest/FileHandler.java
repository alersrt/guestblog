package org.student.guestblog.rest;

import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class FileHandler {

  /** Instance of the {@link GridFsOperations} for managing of the messages' attachments. */
  private final GridFsOperations gridFsOperations;

  public Mono<ServerResponse> getFile(ServerRequest request) {
    GridFsResource resource = gridFsOperations.getResource(request.pathVariable("filename"));
    return ok().contentType(parseMediaType(resource.getContentType())).body(Mono.just(resource), Resource.class);
  }
}
