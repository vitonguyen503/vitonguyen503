import java.io.*;
import java.net.*;

public class testServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server is running and waiting for a connection...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        double height = Double.parseDouble(in.readLine());
        double radius = Double.parseDouble(in.readLine());

        double area = 3.14 * radius * radius * height;

        out.println("Volume: " + area);

        System.out.println("Result sent to client");

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
