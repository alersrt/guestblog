package org.student.guestblog.security.hazelcast;

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

    private final static String MAP_NAME = "persistent_logins";
    private final HazelcastInstance client;
    private final IMap<String, HzPersistentRememberMeToken> tokens;

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

        tokens = client.getMap(MAP_NAME);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokens.put(token.getSeries(), new HzPersistentRememberMeToken(token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
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
        return tokens.get(seriesId).getToken();
    }

    @Override
    public void removeUserTokens(String username) {
        tokens.removeAll(entry -> entry.getValue() != null
            && entry.getValue().getToken() != null
            && entry.getValue().getToken().getUsername() != null
            && entry.getValue().getToken().getUsername().equals(username));
    }
}
