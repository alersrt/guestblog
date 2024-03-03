package org.student.guestblog.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientUserCodeDeploymentConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The root configuration of the application.
 */
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackages = "org.student.guestblog.data.repository")
@Configuration
public class RootConfig {

    /**
     * Returns the object mapper.
     *
     * @return {@link ObjectMapper} bean.
     */
    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }

    @Bean
    public FilterRegistrationBean<OpenEntityManagerInViewFilter> openEntityManagerInViewFilter() {
        FilterRegistrationBean<OpenEntityManagerInViewFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OpenEntityManagerInViewFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

    @Bean
    public HazelcastInstance configuredHazelcastInstance(
        @Value("${hazelcast.server-address}") String hzServerAddress,
        @Value("${hazelcast.cluster-name}") String hzClusterName
    ) {
        var clientUserCodeDeploymentConfig = new ClientUserCodeDeploymentConfig();
        clientUserCodeDeploymentConfig.setEnabled(true);
        clientUserCodeDeploymentConfig.addClass("org.student.guestblog.security.hazelcast.HzPersistentRememberMeToken");

        var clientConfig = new ClientConfig();
        clientConfig.setClusterName(hzClusterName);
        clientConfig
            .getNetworkConfig()
            .addAddress(hzServerAddress);
        clientConfig.setUserCodeDeploymentConfig(clientUserCodeDeploymentConfig);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
