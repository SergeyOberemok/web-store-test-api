package com.finalproject.storeapp.repository;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();
}
