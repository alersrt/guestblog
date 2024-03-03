package org.student.guestblog.security;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.student.guestblog.security.hazelcast.HzPersistentRememberMeToken;

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
        IMap<String, HzPersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        tokens.put(token.getSeries(), new HzPersistentRememberMeToken(token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        IMap<String, HzPersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        var hzToken = tokens.get(series);
        if (hzToken != null && hzToken.getToken() != null) {
            tokens.replace(
                series,
                hzToken,
                new HzPersistentRememberMeToken(new PersistentRememberMeToken(hzToken.getToken().getUsername(), series, tokenValue, lastUsed)));
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        IMap<String, HzPersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        return tokens.get(seriesId).getToken();
    }

    @Override
    public void removeUserTokens(String username) {
        IMap<String, HzPersistentRememberMeToken> tokens = client.getMap(MAP_NAME);
        tokens.removeAll(entry -> entry.getValue() != null
            && entry.getValue().getToken() != null
            && entry.getValue().getToken().getUsername() != null
            && entry.getValue().getToken().getUsername().equals(username));
    }
}
