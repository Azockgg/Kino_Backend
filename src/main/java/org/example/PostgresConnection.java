package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;

public class Postgre {

    private static String USER;
    private static String URL;
    private static String PASSWORD;

    private static void loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Konfigurationsdatei konnte nicht geladen werden.", e);
        }
    }


    Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);



}
