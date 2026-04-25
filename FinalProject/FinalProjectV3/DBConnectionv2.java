package FinalProjectV3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionv2 {
    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USER = "root";
    private static final String PASSWORDS = "fakepw"; //fake password for privacy reasons

    public static void main (String[] args) throws SQLException{
        Connection connection = getConnection();
        UserCRUD userCRUD = new UserCRUD(connection);
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORDS);
    }

}
