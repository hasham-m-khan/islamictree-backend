package com.islamictree.start.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import javax.sql.DataSource;
import java.time.Duration;

@Slf4j
@Configuration
@EnableR2dbcRepositories
public class MySQLDatabaseConfig {

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Value("${spring.r2dbc.name}")
    private String name;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 3306)
                .option(ConnectionFactoryOptions.USER, username)
                .option(ConnectionFactoryOptions.PASSWORD, password)
                .option(ConnectionFactoryOptions.DATABASE, name)
                .option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(30))
                .build());
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Basic pool configuration
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("allowPublicKeyRetrieval", "true");
        config.addDataSourceProperty("serverTimezone", "UTC");
        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");

        return new HikariDataSource(config);
    }

    @Bean
    @Profile("!test")
    public Flyway prodflyway(DataSource dataSource) {
        log.info("Creating production Flyway configuration");

        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .mixed(false)
                .group(true)
                .outOfOrder(false)
                .table("flyway_schema_history")
                .baselineOnMigrate(true)
                .cleanDisabled(true)
                .validateOnMigrate(true)
                .load();
    }

    @Bean
    @Profile("test")
    public Flyway testflyway(DataSource dataSource) {
        log.info("Creating test Flyway configuration with test data");

        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration", "classpath:db/test-data")
                .mixed(true)
                .group(true)
                .outOfOrder(false)
                .table("flyway_schema_history")
                .baselineOnMigrate(true)
                .cleanDisabled(false)
                .validateOnMigrate(true)
                .load();
    }
}
