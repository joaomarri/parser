package com.ef.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Bean(name="dataSource")
    public DataSource dataSource() {
    		BasicDataSource dataSource = new BasicDataSource(); 
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("parseruser");
        dataSource.setPassword("parseruser");
        dataSource.setUrl("jdbc:mysql://localhost:3306/parserdb?profileSQL=false");
        dataSource.setInitialSize(3);
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(8);
        return dataSource;
    }

    @Bean(name="sql")
    public NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
    
}