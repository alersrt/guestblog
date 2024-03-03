package org.student.guestblog.security.hazelcast;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@Setter
public class HzPersistentRememberMeToken implements DataSerializable {

    private String username;
    private String series;
    private String tokenValue;
    private LocalDateTime date;

    public HzPersistentRememberMeToken() {
        this(null, null, null, null);
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
        this.date = in.readObject(LocalDateTime.class);
    }
}
