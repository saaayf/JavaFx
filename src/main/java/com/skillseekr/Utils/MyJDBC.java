package com.skillseekr.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyJDBC {
    // Singleton instance
    private static MyJDBC instance;

    // JDBC connection parameters
    private static final String URL = "jdbc:mysql://localhost/skillseekr";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Connection object
    private Connection connection;

    // Private constructor to prevent instantiation
    private MyJDBC() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get instance method
    public static MyJDBC getInstance() {
        if (instance == null) {
            synchronized (MyJDBC.class) {
                if (instance == null) {
                    instance = new MyJDBC();
                }
            }
        }
        return instance;
    }

    // Get connection method
    public Connection getConnection() {
        return connection;
    }
}
