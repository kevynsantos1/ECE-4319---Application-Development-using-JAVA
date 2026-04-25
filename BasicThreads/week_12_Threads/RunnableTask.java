package week_12_Threads;

public class RunnableTask implements Runnable{
    int id;
    public RunnableTask(int id){
        this.id = id;
    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            System.out.println(id+": New Thread is running..."+i++);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
