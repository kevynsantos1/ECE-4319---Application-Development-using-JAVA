package week_12_Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args){
//        NewThread t1 = new NewThread();
//        t1.setName("MyThread-1");
//        NewThread t2 = new NewThread();
//        t2.setName("MyThread-2");
//        t1.start();
//        t2.start();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new RunnableTask(1));
        executorService.execute(new RunnableTask(2));
    }
}
