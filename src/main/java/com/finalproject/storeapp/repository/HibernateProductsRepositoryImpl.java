package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.repository.shared.CreatePreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service("productsRepository")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class HibernateProductsRepositoryImpl implements ProductsRepository {
    private final static String sqlFetchAll = "select * from products";
    private final static String sqlFetchById = "select * from products where id = ?";

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
                productList.add(getProductFromRow(resultSet));
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return productList;
    }

    @Override
    public Product show(int productId) {
        CreatePreparedStatement createPrepareStatement = (Connection connection) -> {
            PreparedStatement statement = connection.prepareStatement(sqlFetchById);
            statement.setInt(1, productId);
            return statement;
        };

        Product product = null;

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = createPrepareStatement.create(connection);
                ResultSet resultSet = statement.executeQuery();
        ) {
            resultSet.next();
            product = getProductFromRow(resultSet);
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return product;
    }

    @Autowired
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Product getProductFromRow(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getInt("available"),
                resultSet.getDouble("price")
        );
    }
}
