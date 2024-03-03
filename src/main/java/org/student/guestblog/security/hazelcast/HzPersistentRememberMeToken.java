package org.student.guestblog.security.hazelcast;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@FieldNameConstants
@Getter
@Setter
public class HzPersistentRememberMeToken {

    private String username;
    private String seriesId;
    private String tokenValue;
    private LocalDateTime lastUsedDate;
}
