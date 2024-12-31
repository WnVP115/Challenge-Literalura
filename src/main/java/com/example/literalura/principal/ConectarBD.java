package com.example.literalura.principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {

    public static Connection getConnection() throws SQLException{

        String URL = "jdbc:postgresql://localhost:5432/literaluraDB";
        String USER = "postgres";
        String PASSWORD = "159073";

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection){

        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
