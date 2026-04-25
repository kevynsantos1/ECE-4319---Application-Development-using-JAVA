package week_12_Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewThread extends Thread{
    public void run(){
        int i=0;
        while(true){
            System.out.println(this.getName()+": New Thread is running..."+i++);

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
