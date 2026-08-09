package single_thread;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Server{
    public void run( ) throws IOException
     {
      int port =8010;
      ServerSocket socket = new ServerSocket();
      socket.setSoTimeout(10000);
      while(true)
      {
         try
         {
            System.out.println("Server is listening on port " + port);
            Socket acceptedConnection = socket.accept();
            System.out.println("Connection accepted from " + acceptedConnection.getRemoteSocketAddress());
            PrintWriter out=new PrintWriter(acceptedConnection.getOutputStream(),true);
            BufferedReader in=new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
            out.println("hello from the server");

         }
         catch(IOException ex)
         {
            ex.printStackTrace();
         }
      }
    }    
    public static void main(String[] args){
       Server server = new Server();
        try{
           
            server.run();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
