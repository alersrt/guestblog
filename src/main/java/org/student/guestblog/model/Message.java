package org.student.guestblog.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents information about post.
 */
@Entity
public class Message {

    /**
     * Id of the post.
     */
    @Id
    @SequenceGenerator(name = "message_id_gen", sequenceName = "message_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "message_id_gen")
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

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
    private File file;

    /**
     * Time when this post was created.
     */
    @CreationTimestamp
    private LocalDateTime created;

    /**
     * Time whe this post was edited.
     */
    @UpdateTimestamp
    private LocalDateTime updated;

    /**
     * Author of this post.
     */
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Account author;

    public Long getId() {
        return id;
    }

    public Message setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Message setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Message setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public File getFile() {
        return file;
    }

    public Message setFile(File file) {
        this.file = file;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Message setCreated(LocalDateTime createdAt) {
        this.created = createdAt;
        return this;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public Message setUpdated(LocalDateTime updatedAt) {
        this.updated = updatedAt;
        return this;
    }

    public boolean isEdited() {
        return updated != null && updated.isAfter(created);
    }

    public Account getAuthor() {
        return author;
    }

    public Message setAuthor(Account account) {
        this.author = account;
        return this;
    }
}
