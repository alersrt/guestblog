package org.student.guestblog.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

/**
 * Represents information about post.
 */
@DynamicUpdate
@Entity
@Table(schema = "gbsm", name = "message")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity extends BaseEntity {

    @Column(name = "author_id")
    private UUID authorId;

    /**
     * Title of the post.
     */
    @Column(columnDefinition = "text")
    private String title;

    /**
     * Body of the post.
     */
    @Column(columnDefinition = "text")
    private String text;

    /**
     * Attachment.
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    private FileEntity file;

    /**
     * Author of this post.
     */
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private AccountEntity author;
}
