package week_11_SQLMY_Java;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameServer {
    private static final int PORT = 8081;
    private static Connection connection;


    public static void main(String[] args) throws SQLException {
        System.out.println("Game server Started");
        connection = DBConnectionv2.getConnection();
        UserCRUD userCRUD = new UserCRUD(connection);

        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port: " + PORT);

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                //receive username
                String action = (String) inputStream.readObject();
                String username = (String) inputStream.readObject();
                String password = (String) inputStream.readObject();

                System.out.println("Login request from: " + username);

                String response;

                if (action.equalsIgnoreCase("register")) {
                    boolean success = userCRUD.registerUser(username, password);
                    if (success) {
                        response = "Registration successful.";
                    } else {
                        response = "Registration failed. Username already exists.";
                    }
                } else if (action.equalsIgnoreCase("login")) {
                    boolean success = userCRUD.loginUser(username, password);
                    if (success) {
                        response = "Login successful. Welcome, " + username + "!";
                    } else {
                        response = "Login failed. Invalid username or password.";
                    }
                } else {
                    response = "Invalid action.";
                }


                outputStream.writeObject(response);
                //System.out.println("Closing connection");
                socket.close();
            }

        }catch(IOException e){
            throw new RuntimeException(e);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static User getUser(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int score = resultSet.getInt("Score");
                System.out.println("Welcome back"+username+"(highest score="+score+")");
            }
            else {
                return addUser(username);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return new week_11_SQLMY_Java.User(username, 0);
    }

    public static User addUser(String username){
        String sql = "INSERT INTO users(username) VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            System.out.println("New user registration: "+username);
            return new week_11_SQLMY_Java.User(username, 0);

        }catch (SQLException e){
            System.out.println("Error adding user: " + username + e.getMessage());
            e.printStackTrace();
        }
        return new User(username,0);
    }


}
