package com.org.cache;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteCacheDatabase {

    public static void initialize(){

        String sql =" CREATE TABLE IF NOT EXISTS `product_cache`(" +
                "cache_key TEXT PRIMARY KEY," +
                "payload TEXT NOT NULL)";

        try {

            Connection connection = SQLiteConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
