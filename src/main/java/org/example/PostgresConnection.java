package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnection {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String DB_URL = properties.getProperty("db.url");
    public static final String DB_USER = properties.getProperty("db.user");
    public static final String DB_PASSWORD = properties.getProperty("db.password");


    public void getConnection() {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Verbindung erfolgreich");
            connection.close();
        } catch (Exception e) {
            System.out.println("Verbindung Fehlgeschlagen");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        PostgresConnection postgresConnection = new PostgresConnection();
        postgresConnection.getConnection();
    }
}


