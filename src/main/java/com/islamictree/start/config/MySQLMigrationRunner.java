package com.islamictree.start.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class MySQLMigrationRunner {

    private final Flyway flyway;

    public MySQLMigrationRunner(Flyway flyway) {
        this.flyway = flyway;
    }

    @Bean
    @ConditionalOnMissingBean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
    }

    @PostConstruct
    public void migrate() {
        try {
            log.info("Starting MySQL database migration...");

            try {
                flyway.repair();
                log.info("Flyway repair completed successfully.");
            } catch (Exception repairException) {
                log.warn("Flyway repair failed, but continuing with migration: {}", repairException.getMessage());
            }

            MigrateResult result = flyway.migrate();
            log.info("Migration completed successfully. Applied {} migtrations",
                    result.migrationsExecuted);
        } catch (Exception e) {
            log.error("Migration failed", e);
            throw new RuntimeException("Database migration failed", e);
        }
    }
}
