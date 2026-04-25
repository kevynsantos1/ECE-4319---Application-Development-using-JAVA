package week_11_SQL_Java;


import javax.sound.sampled.Port;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USER = "root";
    private static final String PASSWORDS = "fakepw"; //deleted my database password for privacy reasons.

    public static void main (String[] args) throws SQLException{
        Connection connection = getConnection();
        UserCRUD userCRUD = new UserCRUD(connection);
//
//        userCRUD.addUser("kevyneleven");
//        userCRUD.listUsers();
//
//        userCRUD.updateUser(5, "legomyeggo");
//        userCRUD.listUsers();
//
//        userCRUD.deleteUser(5);
//        userCRUD.listUsers();


    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORDS);
    }

}
