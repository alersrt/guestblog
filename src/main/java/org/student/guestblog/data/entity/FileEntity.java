package org.student.guestblog.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Represents file emtity.
 */
@DynamicUpdate
@Entity
@Table(schema = "gbsm", name = "file")
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
