package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service("productsRepository")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class HibernateProductsRepositoryImpl implements ProductsRepository {

    private DatabaseConnection databaseConnection;

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();

        try (

                Connection connection = databaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from products");
        ) {
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("available"),
                        resultSet.getDouble("price"))
                );
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return productList;
    }

    @Autowired
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
