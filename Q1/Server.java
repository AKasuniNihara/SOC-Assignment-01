import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println("Received: " + message);
                    if (message.equalsIgnoreCase("terminate")) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

