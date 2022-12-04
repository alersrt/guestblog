package org.student.guestblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

/**
 * Represents file emtity.
 */
@Entity
public class File {

    /**
     * Id of the post.
     */
    @Id
    @SequenceGenerator(name = "file_id_gen", sequenceName = "file_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "file_id_gen")
    private Long id;

    /**
     * Filename.
     */
    private String filename;

    /**
     * Mime type of this file.
     */
    private String mime;

    /**
     * Binary data.
     */
    private byte[] blob;

    public Long getId() {
        return id;
    }

    public File setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public File setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getMime() {
        return mime;
    }

    public File setMime(String mime) {
        this.mime = mime;
        return this;
    }

    public byte[] getBlob() {
        return blob;
    }

    public File setBlob(byte[] blob) {
        this.blob = blob;
        return this;
    }
}
