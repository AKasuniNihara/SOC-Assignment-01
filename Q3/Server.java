import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    
    private static final Map<String, List<Socket>> topicSubscribers = new ConcurrentHashMap<>();

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
            String topic = input.readLine();
            if ("SUBSCRIBER".equals(mode)) {
                topicSubscribers.putIfAbsent(topic, Collections.synchronizedList(new ArrayList<>()));
                topicSubscribers.get(topic).add(client);
            }

            String message;
            while ((message = input.readLine()) != null) {
                if ("PUBLISHER".equals(mode)) {
                    
                    List<Socket> subscribers = topicSubscribers.getOrDefault(topic, new ArrayList<>());
                    synchronized (subscribers) {
                        for (Socket subscriber : subscribers) {
                            PrintWriter out = new PrintWriter(subscriber.getOutputStream(), true);
                            out.println("[" + topic + "] " + message);
                        }
                    }
                }
            }

        } catch (Exception e) {
           
        }
    }
}

