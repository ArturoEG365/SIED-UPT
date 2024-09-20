package com.sied.clients.config.general;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * SchedulerConfig is a configuration class for enabling JPA repositories in the application.
 * It is marked with @Configuration to indicate that it is a source of bean definitions,
 * and @EnableJpaRepositories to enable the creation of JPA repositories.
 */
@Configuration
@EnableJpaRepositories
public class SchedulerConfig {
    // This class is intentionally left blank. It serves as a configuration class
    // for enabling JPA repositories in the Spring context.
}
