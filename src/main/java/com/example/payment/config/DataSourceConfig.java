/*
 * DataSourceConfig
 *
 * Creates the application's PostgreSQL DataSource.
 *
 * In AWS, the database username/password are retrieved from
 * AWS Secrets Manager rather than stored in the EC2 .env file.
 */
package com.example.payment.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("aws")
public class DataSourceConfig {

    @Bean
    public HikariDataSource dataSource(
            AwsSecretsService secretsService,
            @Value("${app.aws.secret-name}") String secretName,
            @Value("${spring.datasource.url}") String databaseUrl) {

        RdsCredentials credentials =
                secretsService.getRdsCredentials(secretName);

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(databaseUrl);
        dataSource.setUsername(credentials.username());
        dataSource.setPassword(credentials.password());

        return dataSource;
    }
}