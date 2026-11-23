package org.student.guestblog.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Represents information about post.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity extends BaseEntity {

    private UUID authorId;

    /**
     * Title of the post.
     */
    private String title;

    /**
     * Body of the post.
     */
    private String text;

    /**
     * Attachment.
     */
    private FileEntity file;

    /**
     * Author of this post.
     */
    private AccountEntity author;
}
