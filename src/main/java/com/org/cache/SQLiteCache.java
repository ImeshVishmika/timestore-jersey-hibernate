package com.org.cache;

import java.sql.*;

public class SQLiteCache {

//    public static String get(String key){
//
//        String data = "";
//
//        String sql = "SELECT `payload` FROM `product_cache` " +
//                " WHERE `cache_key`= ?";
//
//        try {
//
//            Connection connection = SQLiteConnection.getConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1,"key");
//
//            ResultSet rs = statement.executeQuery();
//
//            if(rs.next()){
//                return null;
//            }
//
//            data=rs.getString("product_name");
//
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//
//        return data;
//    }

    public static boolean put(int key,String payload){
        boolean state = true;
        String sql = "INSERT INTO `product_cache` VALUES(?,?)";

        try {

            Connection connection = SQLiteConnection.getConnection();
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,key);
            st.setString(2,payload);

        }catch (SQLException e){
            state =false;
            System.out.println(e.getMessage());
        }

        return state;
    }
}
