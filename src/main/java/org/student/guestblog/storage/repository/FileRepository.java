package org.student.guestblog.storage.repository;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.student.guestblog.storage.entity.FileEntity;
import org.student.guestblog.storage.jooq.tables.File;
import org.student.guestblog.storage.jooq.tables.records.FileRecord;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class FileRepository {

    private final DSLContext dsl;

    public FileRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<FileEntity> findById(UUID id) {
        return dsl.selectFrom(File.FILE)
            .where(File.FILE.GID.eq(id))
            .fetchOptional()
            .map(this::map);
    }

    public Optional<FileEntity> findByFilename(String filename) {
        return dsl.selectFrom(File.FILE)
            .where(File.FILE.FILENAME.eq(filename))
            .fetchOptional()
            .map(this::map);
    }

    public FileEntity save(FileEntity entity) {
        var now = OffsetDateTime.now();
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(now);
        }
        entity.setUpdateDate(now);

        dsl.insertInto(File.FILE)
            .set(File.FILE.GID, entity.getId())
            .set(File.FILE.CREATE_DATE, entity.getCreateDate())
            .set(File.FILE.UPDATE_DATE, entity.getUpdateDate())
            .set(File.FILE.FILENAME, entity.getFilename())
            .set(File.FILE.MIME, entity.getMime())
            .set(File.FILE.BLOB, entity.getBlob())
            .onConflict(File.FILE.GID)
            .doUpdate()
            .set(File.FILE.UPDATE_DATE, entity.getUpdateDate())
            .set(File.FILE.FILENAME, entity.getFilename())
            .set(File.FILE.MIME, entity.getMime())
            .set(File.FILE.BLOB, entity.getBlob())
            .execute();

        return findById(entity.getId())
            .orElseThrow(() -> new IllegalStateException("Stored file is not found"));
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(File.FILE)
            .where(File.FILE.GID.eq(id))
            .execute();
    }

    public void delete(FileEntity entity) {
        deleteById(entity.getId());
    }

    private FileEntity map(FileRecord record) {
        return FileEntity.builder()
            .id(record.getGid())
            .createDate(record.getCreateDate())
            .updateDate(record.getUpdateDate())
            .filename(record.getFilename())
            .mime(record.getMime())
            .blob(record.getBlob())
            .build();
    }
}
