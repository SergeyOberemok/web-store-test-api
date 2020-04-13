package com.finalproject.storeapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service("databaseConnection")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class MysqlConnection implements DatabaseConnection {

    @Autowired
    private Environment env;

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(env.getProperty("spring.datasource.driver-class-name"));
        } catch (ClassNotFoundException error) {
            return null;
        }

        try {
            connection = DriverManager.getConnection(
                    env.getProperty("spring.datasource.url"),
                    env.getProperty("spring.datasource.username"),
                    env.getProperty("spring.datasource.password")
            );

            System.out.println("Database connected");
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return connection;
    }
}
