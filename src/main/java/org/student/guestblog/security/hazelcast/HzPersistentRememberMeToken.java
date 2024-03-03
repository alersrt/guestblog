package org.student.guestblog.security.hazelcast;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.time.LocalDateTime;


public class HzPersistentRememberMeToken implements DataSerializable {

    private String username;
    private String series;
    private String token;
    private LocalDateTime date;

    public HzPersistentRememberMeToken() {
        this(null, null, null, null);
    }

    private HzPersistentRememberMeToken(String username,
                                        String series,
                                        String token,
                                        LocalDateTime date) {
        this.username = username;
        this.series = series;
        this.token = token;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String tokenValue) {
        this.token = tokenValue;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(this.series);
        out.writeString(this.username);
        out.writeString(this.token);
        out.writeObject(this.date);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.series = in.readString();
        this.username = in.readString();
        this.token = in.readString();
        this.date = in.readObject(LocalDateTime.class);
    }
}
