package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.shared.CreatePreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service("userRepository")
public class HibernateUserRepositoryImpl implements UserRepository {
    final static private String sqlFetch = "SELECT * from `users` where `users`.`email` = ?";
    final static private String sqlStore = "INSERT INTO `users` (`email`, `password`) VALUES (?, ?)";

    private DatabaseConnection databaseConnection;

    @Override
    public User fetch(User user) {
        CreatePreparedStatement createPreparedStatement = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlFetch);
            preparedStatement.setString(1, user.getEmail());
            return preparedStatement;
        };

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = createPreparedStatement.create(connection);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (!resultSet.next()) {
                return null;
            }
            ;

            User userFromDb = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("email")
            );
            userFromDb.setHashedPassword(resultSet.getString("password"));

            return userFromDb;
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return null;
    }

    @Override
    public User store(User user) {
        CreatePreparedStatement createPreparedStatement = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStore, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement;
        };

        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = createPreparedStatement.create(connection);
        ) {
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Storing user failed, no rows affected");
            }

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));

                    return user;
                } else {
                    throw new SQLException("Storing user failed, no ID obtained");
                }
            }
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

        return null;
    }

    @Autowired
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
