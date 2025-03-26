package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private Connection connection;


    public PostgresConnection() {

        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Verbindung erfolgreich");
        } catch (Exception e) {
            System.out.println("Verbindung Fehlgeschlagen");
            e.printStackTrace();
        }

    }

    public static ResultSet executeQuery(String querry) {
        try {
            PostgresConnection postgres = new PostgresConnection();
            Statement st = null;
            st = postgres.connection.createStatement();

            ResultSet rs = st.executeQuery(querry);
            while (rs.next()) {
                System.out.print("Column returned ");


                return rs;
            }

        } catch (SQLException e) {
            System.out.println("Querry Fehlgeschlagen");
            e.printStackTrace();
        }
        return null;

    }

    public static List<Object[]> fetchQuery(String query) throws SQLException {
        List<Object[]> resultList = new ArrayList<>();
        PostgresConnection postgres = new PostgresConnection();
        Statement st = postgres.connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();
        try {

            final int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Object[] values = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    values[i - 1] = metaData.getColumnName(i) + ":" + rs.getString(i);
                }
                resultList.add(values);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }


    public static void main(String[] args) throws SQLException {


        {
            List<Object[]> movies = fetchQuery("SELECT * FROM movie");
            for (Object[] movie : movies) {
                System.out.println(Arrays.toString(movie));
            }


        }


    }
}









