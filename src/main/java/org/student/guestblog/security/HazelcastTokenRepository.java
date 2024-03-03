package org.student.guestblog.security;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Token repository with Hazelcast backend.
 */
@Component
public class HazelcastTokenRepository implements PersistentTokenRepository {

    private final HazelcastInstance client;
    private final String MAP_NAME = "persistent_logins";

    public HazelcastTokenRepository(
        @Value("${hazelcast.server-address}") String hzServerAddress,
        @Value("${hazelcast.cluster-name}") String hzClusterName
    ) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(hzClusterName);
        clientConfig
            .getNetworkConfig()
            .addAddress(hzServerAddress);

        client = HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        IMap<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        tokens.put(token.getSeries(), token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        IMap<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        var token = tokens.get(series);
        if (token != null) {
            tokens.replace(series, token, new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        IMap<String, PersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
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
