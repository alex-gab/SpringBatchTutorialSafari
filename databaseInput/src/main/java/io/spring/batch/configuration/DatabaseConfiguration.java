package io.spring.batch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.
                create().
                driverClassName(driverClassName).
                url(url).
                username(user).
                password(password).
                build();
    }
}
