package week_6_1_MiniProjectBest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionLoader {

    public HashMap<Integer, ArrayList<String>> map = new HashMap<>();
    public HashMap<Integer, ArrayList<String>> readQuestions(String filePath){
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

}
