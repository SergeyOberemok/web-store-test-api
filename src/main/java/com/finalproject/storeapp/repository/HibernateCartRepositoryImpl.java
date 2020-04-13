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

@Service("cartRepository")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class HibernateCartRepositoryImpl implements CartRepository {
    final static private String sqlFetchAll = "SELECT `cart`.`id` as cart_id, `cart`.`quantity`, `products`.* FROM `cart` inner JOIN `products` on `cart`.`product_id` = `products`.`id`";

    private DatabaseConnection databaseConnection;

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlFetchAll);
        ) {
            while (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                int available = resultSet.getInt("available") - quantity;

                productList.add(new Product(
                        resultSet.getInt("cart_id"),
                        resultSet.getString("title"),
                        available,
                        resultSet.getDouble("price"),
                        quantity
                ));
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
