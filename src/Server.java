import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dataInputStream = null;
    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started!");
            socket = serverSocket.accept();
            System.out.println("Client accepted!");

            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String tmp = "";
            while(!tmp.equals("over")){
                try{
                    tmp = dataInputStream.readUTF();
                    System.out.println(tmp);
                } catch (IOException ex){
                    ex.printStackTrace();
                }

            }
            System.out.println("Close connection!");
            socket.close();
            dataInputStream.close();
            serverSocket.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(5000);
    }
}
