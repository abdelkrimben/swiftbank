package com.swiftbank.dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
        private static final String URL = "jdbc:mariadb://mysql-databasercb.alwaysdata.net:3306/databasercb_02";
        private static final String USER = "389742_rcb";
        private static final String PASSWORD = "$389742_RCB.";

        public static Connection getConnection() throws SQLException {
                return DriverManager.getConnection(URL, USER, PASSWORD);
        }
}