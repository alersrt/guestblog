package org.student.guestblog.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

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
