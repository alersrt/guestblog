package org.student.guestblog.data.entity;


import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.student.guestblog.model.Authority;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents information about user which is stored in the database.
 */
@DynamicUpdate
@Entity
@Table(schema = "gbsm", name = "account")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PassportEntity> passports = new ArrayList<>();

    /**
     * E-mail of the user.
     */
    @NotBlank
    @Email
    private String email;

    private String name;

    private String avatar;

    /**
     * List of the user's messages.
     */
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MessageEntity> messages = new ArrayList<>();

    /**
     * Authorities of this user.
     */
    @Type(ListArrayType.class)
    @Column(columnDefinition = "varchar[]")
    private List<String> authorities = new ArrayList<>();
}
