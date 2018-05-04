package org.student.guestblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.student.guestblog.entity.Post;
import org.student.guestblog.repository.PostRepository;

/** Service layer for managing of {@link Post}. */
public class PostService {

  /** Bean of the {@link PostRepository}. */
  @Autowired private PostRepository postRepository;

  /**
   * Returns all posts which are contained in database.
   *
   * @return {@link List} of the {@link Post}.
   */
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }
}
