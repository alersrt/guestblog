package org.student.guestblog.security.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.sql.SqlService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

/**
 * Token repository with Hazelcast backend.
 */
@Component
public class HazelcastTokenRepository implements PersistentTokenRepository {

    private static final String MAP_NAME = "persistent_logins";
    private static final String SQL_MAPPING = """
        CREATE MAPPING IF NOT EXISTS persistent_logins (
            __key       VARCHAR,
            series      VARCHAR,
            username    VARCHAR,
            token       VARCHAR,
            "date"      TIMESTAMP
        )
        TYPE IMap
        OPTIONS (
            'keyFormat'='varchar',
            'valueFormat'='java',
            'valueJavaClass'='org.student.guestblog.security.hazelcast.HzPersistentRememberMeToken'
        )
        """;
    private static final String SQL_INSERT = """
        INSERT INTO persistent_logins (__key, series, username, token, "date")
        VALUES (?, ?, ?, ?, ?)
        """;
    private static final String SQL_UPDATE = """
        UPDATE persistent_logins
        SET token = ?,
            "date" = ?
        WHERE series = ?
        """;
    private static final String SQL_DELETE = """
        DELETE FROM persistent_logins
        WHERE username = ?
        """;
    private static final String SQL_SELECT = """
        SELECT *
        FROM persistent_logins
        WHERE series = ?
        """;

    private final SqlService sql;

    public HazelcastTokenRepository(@Qualifier("configuredHazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.sql = hazelcastInstance.getSql();
        this.sql.execute(SQL_MAPPING);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        this.sql.execute(
            SQL_INSERT,
            token.getSeries(),
            token.getSeries(),
            token.getUsername(),
            token.getTokenValue(),
            token.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        this.sql.execute(SQL_UPDATE,
            tokenValue,
            lastUsed,
            series);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        var sqlResult = this.sql.execute(SQL_SELECT, seriesId);

        var row = sqlResult.iterator().next();
        return new PersistentRememberMeToken(
            row.getObject("username"),
            row.getObject("series"),
            row.getObject("token"),
            row.getObject("date")
        );
    }

    @Override
    public void removeUserTokens(String username) {
        this.sql.execute(SQL_DELETE, username);
    }
}
