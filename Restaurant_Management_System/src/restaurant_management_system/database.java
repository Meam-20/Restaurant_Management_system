/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant_management_system;


import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author User
 */
public class database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Connect your database
          //  Connection connect = DriveManager.getConnection("");
            //return connect;
        }catch (Exception e){e.printStackTrace();
    }
    return null;
    }
}
