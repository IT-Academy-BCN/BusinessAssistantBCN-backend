package com.businessassistantbcn.mydata.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EntityScan("com.businessassistantbcn.mydata.entities.*")
@EnableJpaRepositories("com.magnetoresistance.my-data.repository")
@PropertySource("classpath:application-test.properties")
@EnableTransactionManagement
public class SpringDBTestConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driverClasName"));
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(env.getProperty("spring.datasource.user"));
        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
        return dataSourceBuilder.build();
    }


}
