package com.quangtoi.good_news.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
    @Value("${spring.liquibase.url}")
    private String url;
    @Value("${spring.liquibase.password}")
    private String password;
    @Value("${spring.liquibase.user}")
    private String username;
    @Value("${spring.liquibase.change-log}")
    private String changelog;
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelog);
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    private DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .password(password)
                .username(username)
                .build();
    }
}
