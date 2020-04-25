package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.CartProduct;
import com.finalproject.storeapp.repository.shared.CreatePreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service("cartRepository")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class HibernateCartRepositoryImpl implements CartRepository {
    final static private String sqlFetchAll = "SELECT `cart`.`id` as cart_id, `cart`.`quantity`, `products`.* FROM `cart` INNER JOIN `products` ON `cart`.`product_id` = `products`.`id`";
    final static private String sqlFetch = "SELECT `cart`.`id` as cart_id, `cart`.`quantity`, `products`.* FROM `cart` INNER JOIN `products` ON `cart`.`product_id` = `products`.`id` WHERE `cart`.`id` = ?";
    final static private String sqlStore = "INSERT INTO `cart` (`quantity`, `product_id`, `user_id`) VALUES (?, ?, 1)";
    final static private String sqlUpdate = "UPDATE `cart` SET `quantity` = ? WHERE id = ?";
    final static private String sqlDestroy = "DELETE FROM `cart` WHERE id = ?";

    private DatabaseConnection databaseConnection;

    @Override
    public List<CartProduct> findAll() {
        List<CartProduct> cartProductList = new ArrayList<>();

        try (
                Connection connection = databaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlFetchAll);
        ) {
            while (resultSet.next()) {
                cartProductList.add(getCartProductFromRow(resultSet));
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return cartProductList;
    }

    @Override
    public CartProduct show(int cartProductId) {
        CreatePreparedStatement createPreparedStatement = (Connection connection) -> {
            PreparedStatement statement = connection.prepareStatement(sqlFetch);
            statement.setInt(1, cartProductId);
            return statement;
        };

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = createPreparedStatement.create(connection);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                return getCartProductFromRow(resultSet);
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return null;
    }

    @Override
    public CartProduct store(CartProduct cartProduct) {
        CreatePreparedStatement createPreparedStatement = (Connection connection) -> {
            PreparedStatement statement = connection.prepareStatement(sqlStore, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cartProduct.getQuantity());
            statement.setInt(2, cartProduct.getProductId());
            return statement;
        };

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = createPreparedStatement.create(connection);
        ) {
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Storing cart failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cartProduct.setId(generatedKeys.getInt(1));

                    return cartProduct;
                } else {
                    throw new SQLException("Storing cart failed, no ID obtained");
                }
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return null;
    }

    @Override
    public CartProduct update(CartProduct cartProduct) {
        CreatePreparedStatement createPreparedStatement = (Connection connection) -> {
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setInt(1, cartProduct.getQuantity());
            statement.setInt(2, cartProduct.getId());
            return statement;
        };

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = createPreparedStatement.create(connection);
        ) {
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Updating cart failed, no rows affected");
            } else {
                return cartProduct;
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return null;
    }

    @Override
    public int destroy(int cartProductId) {
        CreatePreparedStatement createPreparedStatement = (Connection connection) -> {
            PreparedStatement statement = connection.prepareStatement(sqlDestroy);
            statement.setInt(1, cartProductId);
            return statement;
        };

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = createPreparedStatement.create(connection);
        ) {
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Deleting cart failed, no rows affected");
            }

            return 1;
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return 0;
    }

    @Autowired
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private CartProduct getCartProductFromRow(ResultSet resultSet) throws SQLException {
        int quantity = resultSet.getInt("quantity");
        int available = resultSet.getInt("available") - quantity;

        CartProduct cartProduct = new CartProduct(
                resultSet.getInt("cart_id"),
                resultSet.getString("title"),
                available,
                resultSet.getDouble("price"),
                quantity
        );
        cartProduct.setProductId(resultSet.getInt("id"));

        return cartProduct;
    }
}
