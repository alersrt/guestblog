package org.student.guestblog.security.hazelcast;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class HzPersistentRememberMeToken implements DataSerializable {

    private final static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        // date/time
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        // offset (hh:mm - "+00:00" when it's zero)
        .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
        // offset (hhmm - "+0000" when it's zero)
        .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
        // offset (hh - "Z" when it's zero)
        .optionalStart().appendOffset("+HH", "Z").optionalEnd()
        // create formatter
        .toFormatter();

    private String username;
    private String series;
    private String tokenValue;
    private LocalDateTime date;

    public HzPersistentRememberMeToken() {
        this(null, null, null, null);
    }

    public HzPersistentRememberMeToken(PersistentRememberMeToken token) {
        this(
            token.getUsername(),
            token.getSeries(),
            token.getTokenValue(),
            token.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }

    private HzPersistentRememberMeToken(String username,
                                        String series,
                                        String tokenValue,
                                        LocalDateTime date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(this.series);
        out.writeString(this.username);
        out.writeString(this.tokenValue);
        out.writeObject(this.date);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.series = in.readString();
        this.username = in.readString();
        this.tokenValue = in.readString();
        this.date = LocalDateTime.parse(in.readObject(LocalDateTime.class), formatter);
    }
}
