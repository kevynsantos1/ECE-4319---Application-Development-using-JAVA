package FinalProjectV3;

public class PlayerSession {
    private String username;
    private boolean loggedIn;
    private boolean answeredCurrentQuestion;

    public PlayerSession() {
        username = "";
        loggedIn = false;
        answeredCurrentQuestion = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean hasAnsweredCurrentQuestion() {
        return answeredCurrentQuestion;
    }

    public void setAnsweredCurrentQuestion(boolean answeredCurrentQuestion) {
        this.answeredCurrentQuestion = answeredCurrentQuestion;
    }
}