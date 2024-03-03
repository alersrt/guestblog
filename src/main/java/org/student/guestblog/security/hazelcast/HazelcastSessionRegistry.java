package org.student.guestblog.security.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Session repository with Hazelcast backend.
 */
@Component
public class HazelcastSessionRegistry implements SessionRegistry {

    private static final String MAP_NAME = "user_session";

    private final IMap<String, Object> sessions;

    public HazelcastSessionRegistry(@Qualifier("configuredHazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.sessions = hazelcastInstance.getMap(MAP_NAME);
    }

    @Override
    public List<Object> getAllPrincipals() {
        return sessions.values().stream().toList();
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        return null;
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        return null;
    }

    @Override
    public void refreshLastRequest(String sessionId) {
    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {

    }

    @Override
    public void removeSessionInformation(String sessionId) {

    }
}
