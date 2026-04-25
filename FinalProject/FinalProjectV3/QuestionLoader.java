package FinalProjectV3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionLoader {

    public HashMap<Integer, ArrayList<String>> readQuestions(String filePath){
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(
                    filePath);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int readerCount = 0;

            String line = "";
            while (true){
                line = bufferedReader.readLine();

                if (line == null){
                    break;
                }
                System.out.println(readerCount + ": " + line);
                int questionNumber = readerCount/6 + 1;

                if (readerCount % 6 ==0){
                    map.put(questionNumber, new ArrayList<>());
                }
                map.get(questionNumber).add(line);

                readerCount++;

            }

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    public ArrayList<Question> readQuestionObjects(String filePath) {
        HashMap<Integer, ArrayList<String>> raw = readQuestions(filePath);
        ArrayList<Question> questions = new ArrayList<>();

        for (int i = 1; i <= raw.size(); i++) {
            ArrayList<String> q = raw.get(i);

            String questionText = q.get(0);
            String[] options = {q.get(1), q.get(2), q.get(3), q.get(4)};
            int correctIndex = Integer.parseInt(q.get(5)) - 1;

            questions.add(new Question(questionText, options, correctIndex));
        }

        return questions;
    }

}
