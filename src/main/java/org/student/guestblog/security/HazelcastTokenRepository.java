package org.student.guestblog.security;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.nio.serialization.compact.CompactReader;
import com.hazelcast.nio.serialization.compact.CompactSerializer;
import com.hazelcast.nio.serialization.compact.CompactWriter;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * Token repository with Hazelcast backend.
 */
@Component
public class HazelcastTokenRepository implements PersistentTokenRepository {

    private final HazelcastInstance client;

    private final String MAP_NAME = "persistent_logins";

    public HazelcastTokenRepository() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("gb-hazelcast");
        clientConfig
            .getNetworkConfig()
            .addAddress("localhost", "localhost:5701");
        clientConfig
            .getSerializationConfig()
            .setAllowOverrideDefaultSerializers(true)
            .getCompactSerializationConfig()
            .addSerializer(new CompactSerializer<Date>() {
                @Nonnull
                @Override
                public Date read(@Nonnull CompactReader compactReader) {
                    return Date.from(compactReader.readTimestamp("date").atZone(ZoneId.systemDefault()).toInstant());
                }

                @Override
                public void write(@Nonnull CompactWriter compactWriter, @Nonnull Date date) {
                    compactWriter.writeTimestamp("date", date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                }

                @Nonnull
                @Override
                public String getTypeName() {
                    return Date.class.getTypeName();
                }

                @Nonnull
                @Override
                public Class<Date> getCompactClass() {
                    return Date.class;
                }
            });

        client = HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        Map<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        tokens.put(token.getSeries(), token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Map<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        var token = tokens.get(series);
        if (token != null) {
            tokens.replace(series, token, new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Map<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        return tokens.get(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        IMap<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        tokens.removeAll(entry -> entry.getValue() != null
            && entry.getValue().getUsername() != null
            && entry.getValue().getUsername().equals(username));
    }
}
