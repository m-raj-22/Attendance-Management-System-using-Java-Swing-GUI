package csw_endsem_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Establish a connection to your MySQL database
        Connection connection = connectToMySQL();

        // Check if the connection is successful before proceeding
        if (connection != null) {
            // Call the constructor for the splash screen, passing the MySQL connection
            new Splashscreen();
        } else {
            System.out.println("Failed to connect to the MySQL database.");
        }
    }

    private static Connection connectToMySQL() {
        try {
            // Provide your MySQL database information (url, username, password)
            String url = "jdbc:mysql://localhost:3306/attendance";
            String username = "username";
            String password = "password";

            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
