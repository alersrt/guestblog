package org.student.guestblog.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.student.guestblog.domain.PassportType;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PassportEntity extends BaseEntity {

    private AccountEntity account;

    private PassportType type;

    private String hash;
}
