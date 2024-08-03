package csw_endsem_project;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MySqlConnection {
    public static Connection dbConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance", "username", "password");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
