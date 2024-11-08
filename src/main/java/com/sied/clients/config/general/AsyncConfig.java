package com.sied.clients.config.general;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Puedes configurar un ThreadPool si lo necesitas
}
