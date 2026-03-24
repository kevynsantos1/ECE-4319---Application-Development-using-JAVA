package week_8_2_Networking_Pt2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler {
    private ServerFrame frame;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private List<Question> questions;

    public ClientHandler(ServerFrame frame,
                         Socket socket,
                         List<Question> questions) {
        this.frame = frame;
        this.socket = socket;
        this.questions = questions;
    }

    public void sendQuestionsAndReceiveAnswers() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();

            inputStream = new ObjectInputStream((socket.getInputStream()));

            for (Question question: questions) { //for each of the question elements inside question array
                outputStream.writeObject("Q: " + question.getQuestion());
                for (int i = 0; i < 4; i++) {
                    outputStream.writeObject((i + 1) + ") "
                            + question.getOptions()[i]);
                    outputStream.flush();
                }
                outputStream.writeObject("Your answer is: ");
                outputStream.flush();

                String answer = (String) inputStream.readObject();
                if (frame.validateAnswers(question, answer)) {
                    //scoring
                }
            }
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
