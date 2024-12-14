package com.example.starBank.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RecommendationCounterDataSourceConfiguration {

    @Bean(name = "recommendationCounterDataSource")
    public DataSource recommendationCounterDataSource(@Value("${spring.datasource.url}") String recommendationCounterUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationCounterUrl);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("student");
        dataSource.setPassword("chocolatefrog");
        return dataSource;
    }

    @Bean(name = "recommendationCounterJdbcTemplate")
    public JdbcTemplate recommendationCounterJdbcTemplate(
            @Qualifier("recommendationCounterDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

}
