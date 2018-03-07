package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static Server.MainApp.clientsThreadsMap;

public class ClientThreadOnServer implements Runnable {
    private final Scanner reader;
    private final PrintWriter writer;

    public Socket getClientSocket() {
        return clientSocket;
    }

    private Socket clientSocket;

    public ClientThreadOnServer(Socket inputClientSocket) throws IOException {
        this.clientSocket = inputClientSocket;

        writer = new PrintWriter(inputClientSocket.getOutputStream(), true);
        reader = new Scanner(inputClientSocket.getInputStream());
    }

    @Override
    public void run() {
        String nick = reader.nextLine();
        MainApp.registerUser(nick, this);
        while (reader.hasNextLine()) {
            String message = reader.nextLine();
            System.out.println(message);
            sendToAll(nick, message);
        }
    }

    private void sendToAll(String nick, String message) {
        synchronized (clientsThreadsMap) {
            clientsThreadsMap.entrySet().stream()
                    .filter(a -> !a.getKey().equals(nick))
                    .forEach(a -> a.getValue().send(nick + " sent: " + message));
        }
    }

    private void send(String message) {
        writer.write(message + '\n');
        writer.flush();
    }
}
