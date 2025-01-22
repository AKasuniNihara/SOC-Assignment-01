import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;

public class Server {
    static List<Socket> subscribers = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
        ExecutorService pool = Executors.newCachedThreadPool();
        System.out.println("Server is running...");
        while (true) {
            Socket client = server.accept();
            pool.execute(() -> handleClient(client));
        }
    }

    private static void handleClient(Socket client) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String mode = input.readLine();
            if ("SUBSCRIBER".equals(mode)) {
                subscribers.add(client);
            }
            String message;
            while ((message = input.readLine()) != null) {
                if ("PUBLISHER".equals(mode)) {
                    synchronized (subscribers) {
                        for (Socket subscriber : subscribers) {
                            PrintWriter out = new PrintWriter(subscriber.getOutputStream(), true);
                            out.println(message);
                        }
                    }
                }
            }
        } catch (Exception e) {
            
        }
    }
}

