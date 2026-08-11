package single_thread;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;   
import java.net.InetAddress;
public class Client {
    public void run() throws UnknownHostException, IOException {
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, port);
        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toSocket.println("hello from the client");
        System.out.println("response from the server" +fromSocket.readLine());
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            Client client = new Client();
            client.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
