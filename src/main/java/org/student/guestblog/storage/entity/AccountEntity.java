package org.student.guestblog.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents information about user which is stored in the database.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {

    @Builder.Default
    private List<PassportEntity> passports = new ArrayList<>();

    /**
     * E-mail of the user.
     */
    private String email;

    private String name;

    private String avatar;

    /**
     * List of the user's messages.
     */
    @Builder.Default
    private List<MessageEntity> messages = new ArrayList<>();

    /**
     * Authorities of this user.
     */
    @Builder.Default
    private List<String> authorities = new ArrayList<>();
}
