package org.student.guestblog.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

/** Represents information about post. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {

  /** Id of the post. */
  @Id
  @GeneratedValue
  private long id;

  /** Title of the post. */
  @Size(max = 100)
  private String title;

  /** Body of the post. */
  @Size(max = 1024)
  private String text;

  /** Attachment. */
  @OneToOne(cascade = CascadeType.REMOVE)
  private File file;

  /** Time when this post was created or edited. */
  @CreationTimestamp
  private Date timestamp;

  /** Flag determines was this post edited or no. */
  private boolean edited;

  /** Author of this post. */
  @ManyToOne
  private User user;
}
