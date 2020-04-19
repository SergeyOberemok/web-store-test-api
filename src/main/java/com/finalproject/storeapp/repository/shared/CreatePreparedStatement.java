package com.finalproject.storeapp.repository.shared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface CreatePreparedStatement {
    PreparedStatement create(Connection connection) throws SQLException;
}
