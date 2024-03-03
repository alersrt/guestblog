package org.student.guestblog.security.hazelcast;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.time.LocalDateTime;


public class HzPersistentRememberMeToken implements DataSerializable {

    private String username;
    private String series;
    private String tokenValue;
    private LocalDateTime lastUsedDate;

    public HzPersistentRememberMeToken() {
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

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public LocalDateTime getLastUsedDate() {
        return lastUsedDate;
    }

    public void setLastUsedDate(LocalDateTime lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(this.series);
        out.writeString(this.username);
        out.writeString(this.tokenValue);
        out.writeObject(this.lastUsedDate);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.series = in.readString();
        this.username = in.readString();
        this.tokenValue = in.readString();
        this.lastUsedDate = in.readObject(LocalDateTime.class);
    }
}
