package com.example.mykpp4;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ApartamentsOSBB;encrypt=false;";
    private static final String USER = "medoko"; // або ваше ім'я користувача
        private static final String PASSWORD = "1234567890"; // пароль користувача

        public static Connection getConnection() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection successful!");
            } catch (SQLException e) {
                System.out.println("Connection failed: " + e.getMessage());
            }
            return conn;
        }
    }