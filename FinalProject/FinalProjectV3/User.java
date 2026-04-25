package FinalProjectV3;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private int score;
    // other fields, such as password

    public User(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; } //LOL in the real world this is probably not what we would do
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
}

