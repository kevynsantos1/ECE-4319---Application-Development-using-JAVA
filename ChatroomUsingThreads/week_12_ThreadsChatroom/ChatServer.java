package week_12_ThreadsChatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT=8083;
    private static final Set<PrintWriter> clientWriters = ConcurrentHashMap.newKeySet(); //we create a thread-safe Set by a ConcurrentHashMap. clientWriters stores all the PrintWriter objects for each connected client. We want a global set of all PrintWriters so we can iterate and send out the same message

    public static void main(String[] args){
        System.out.println("Chat server running on port: "+PORT+"...");
        ExecutorService pool = Executors.newFixedThreadPool(20);

        try{
            ServerSocket serverSocket = new ServerSocket(PORT);


            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client connected: " + socket);
                pool.execute(new ClientHandler(socket));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private static class ClientHandler implements Runnable{
        private Socket socket;
        private PrintWriter out; //another option for output object stream
        private BufferedReader in; //another option for input object stream
        //we use PW and BR because they are multithread friendly

        public ClientHandler(Socket socket){
            this.socket=socket;
        }


        @Override
        public void run(){
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(out);


                out.println("Welcome to the chat!");
                broadcast("A new user has joined the chat!");

                String message;
                while((message = in.readLine()) != null){
                    if(message.equalsIgnoreCase("bye")){
                        break;
                    }
                    System.out.println("Received: "+message);
                    broadcast(message);
                }
            }catch(IOException e){
                System.out.println("Connection lost: "+socket);
            }finally { //finally is used for exception handling for when everything is done
                clientWriters.remove(out);
                try{
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                broadcast("A user has left the chat");
                System.out.println("Client disconnected: "+socket);
            }
        }


    }

    private static void broadcast(String message){
        for(PrintWriter writer : clientWriters){
            writer.println(message);
        }
    }



}
