package com.github.mcinerneym.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class FlywayConfigTest {

    @Test
    void migrateConfiguresDataSourceAndRunsFlywayMigration() {
        DataSource dataSource = mock(DataSource.class);
        FluentConfiguration fluentConfiguration = mock(FluentConfiguration.class);
        Flyway flyway = mock(Flyway.class);

        try (MockedStatic<Flyway> flywayStaticMock = mockStatic(Flyway.class)) {
            flywayStaticMock.when(Flyway::configure).thenReturn(fluentConfiguration);
            when(fluentConfiguration.dataSource(dataSource)).thenReturn(fluentConfiguration);
            when(fluentConfiguration.load()).thenReturn(flyway);

            FlywayConfig flywayConfig = new FlywayConfig(dataSource);
            flywayConfig.migrate();

            verify(fluentConfiguration).dataSource(dataSource);
            verify(fluentConfiguration).load();
            verify(flyway).migrate();
        }
    }
}
