package com.github.mcinerneym.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class FlywayConfig {
    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void migrate() {
        Flyway.configure()
            .dataSource(dataSource)
            .load()
            .migrate();
    }
}
