package Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {
    public static int portNumber = 12345;
    public static final ExecutorService executor = Executors.newFixedThreadPool(20);
    public static final Map<String, ClientThreadOnServer> clientsThreadsMap = new HashMap<>();
    public static void main(String[] args) throws IOException {


        System.out.println("JAVA TCP SERVER");

        JavaTcpServerStarter t = new JavaTcpServerStarter("sockecik");
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.start();
        new JavaUdpServerStarter().start();

    }

    public static boolean registerUser(String name, ClientThreadOnServer clientThreadOnServer) {
        clientsThreadsMap.put(name, clientThreadOnServer);
        System.out.println("New user joined, name: " + name);
        return true;
    }

}
