package org.student.guestblog.config.security;

import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.student.guestblog.data.repository.AccountRepository;
import org.student.guestblog.security.User;
import org.student.guestblog.security.oauth2.CustomOAuth2UserService;
import org.student.guestblog.security.session.SimpleSessionInformationExpiredStrategy;
import org.student.guestblog.util.Cookie;

@RequiredArgsConstructor
@Configuration
@EnableHazelcastHttpSession
@EnableMethodSecurity(
    proxyTargetClass = true,
    prePostEnabled = true,
    jsr250Enabled = true
)
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    private static final Directive[] SOURCE = {
        Directive.CACHE,
        Directive.COOKIES,
        Directive.STORAGE,
        Directive.EXECUTION_CONTEXTS
    };

    private final AccountRepository accountRepository;
    private final PersistentTokenRepository persistentTokenRepository;
    @Qualifier("configuredHazelcastInstance")
    private final HazelcastInstance hazelcastInstance;

    @Bean
    public UserDetailsService customUserDetailsServiceBean() {
        return username -> {
            var account = accountRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

            return new User(account);
        };
    }

    /**
     * Password's encoder.
     *
     * @return {@link BCryptPasswordEncoder} bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserServiceBean() {
        return new CustomOAuth2UserService(accountRepository);
    }

    @Bean
    public SpringSessionBackedSessionRegistry<?> sessionRegistry() {
        var sessionRepository = new HazelcastIndexedSessionRepository(hazelcastInstance);
        sessionRepository.setSessionMapName("persistent_sessions");
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // Disable CORS and disable CSRF
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            // Set session management to never created
            .sessionManagement(smc -> smc
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .sessionFixation()
                .migrateSession()
                .sessionConcurrency(concurrencyControlConfigurer -> {
                    concurrencyControlConfigurer.sessionRegistry(sessionRegistry());
                    concurrencyControlConfigurer.expiredSessionStrategy(new SimpleSessionInformationExpiredStrategy());
                })
            )
            // Set request cache to null
            .requestCache(rcc -> rcc.requestCache(new NullRequestCache()))
            // Setup login
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(formLoginConfigurer -> formLoginConfigurer
                .loginProcessingUrl("/api/auth/login")
                .successHandler((request, response, authentication) -> {
                })
                .failureHandler(new SimpleUrlAuthenticationFailureHandler()))
            .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                .alwaysRemember(true)
                .tokenValiditySeconds(24 * 60 * 60)
                .useSecureCookie(true)
                .rememberMeCookieName(Cookie.X_AUTH_REMEMBER_ME)
                .userDetailsService(customUserDetailsServiceBean())
                .tokenRepository(persistentTokenRepository))
            // Setup logout
            .logout(logoutConfigurer -> logoutConfigurer
                .permitAll()
                .logoutUrl("/api/auth/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            )
            // Setup permissions on endpoints
            .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry.anyRequest().permitAll())
            // OAuth2
            .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserServiceBean()))
            )

            .build();
    }
}
