package FinalProjectV3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUD {
    private final Connection connection;

    public UserCRUD(Connection connection){
        this.connection = connection;
    }

    public synchronized boolean usernameCheck(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean registerUser(String username, String password) {
        if (usernameCheck(username)) {
            System.out.println("USERNAME TAKEN!");
            return false;
        }

        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("User registered: " + username);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("[LOGIN SUCCESS] User: " + username);
                return true;
            } else {
                System.out.println("[LOGIN FAILED] Invalid credentials for user: " + username);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized void listUsers() {
        String sql = "SELECT * FROM users";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Current users: ");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateUser(int id, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newUsername);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();

            System.out.println(rows > 0 ? "User updated!" : "User not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "User deleted." : "User not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<User> getTopUsers(int limit) {
        List<User> leaderboard = new ArrayList<>();
        String sql = "SELECT username, score FROM users ORDER BY score DESC, username ASC LIMIT ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, limit);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                leaderboard.add(new User(username, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }

    public synchronized void updateHighScore(String username, int newScore) {
        String sql = "UPDATE users SET score = ? WHERE username = ? AND score < ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, newScore);
            pstmt.setString(2, username);
            pstmt.setInt(3, newScore);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}