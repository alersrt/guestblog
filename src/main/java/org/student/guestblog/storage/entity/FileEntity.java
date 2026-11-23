package org.student.guestblog.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * Represents file entity.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity extends BaseEntity {

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
}
