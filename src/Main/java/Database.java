package Main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
        String url = "jdbc:mysql://localhost:3306/flippielyzer";
        String username = "root";
        String password = "Tyson1990!";
        public Connection connect(){
                Connection con = null;
                // connecten tot de database
                try {
                        con = DriverManager.getConnection(url, username, password);

                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
                return con;
        }
}
