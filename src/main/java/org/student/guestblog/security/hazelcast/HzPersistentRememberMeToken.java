package org.student.guestblog.security.hazelcast;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import lombok.Getter;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

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

    @Getter
    private PersistentRememberMeToken token;

    public HzPersistentRememberMeToken() {
        this.token = null;
    }

    public HzPersistentRememberMeToken(PersistentRememberMeToken token) {
        this.token = token;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(token.getSeries());
        out.writeString(token.getUsername());
        out.writeString(token.getTokenValue());
        out.writeString(token.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        var series = in.readString();
        var username = in.readString();
        var tokenValue = in.readString();
        var date = Date.from(LocalDateTime.parse(in.readString(), formatter).atZone(ZoneId.systemDefault()).toInstant());

        this.token = new PersistentRememberMeToken(username, series, tokenValue, date);
    }
}
