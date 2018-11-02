package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
        DataSource ds = context.getBean(DataSource.class);
        System.out.println(ds.getClass().getName());
        Connection connection = ds.getConnection();
        System.out.println(connection.getCatalog());
        System.out.println(context.getBean(JdbcTemplate.class));
        connection.close();
    }
}
