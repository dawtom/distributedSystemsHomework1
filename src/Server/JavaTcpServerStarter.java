package Server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class JavaTcpServerStarter extends Thread {

    private String threadName;
    private Socket clientSocket;

    JavaTcpServerStarter(String name) {
        threadName = name;
//        System.out.println("Creating " +  threadName );
    }

    public void run() {
//        System.out.println("Running " +  threadName );

        try  {
            ServerSocket serverSocket = new ServerSocket(MainApp.portNumber);
            while (!serverSocket.isClosed()){
                this.clientSocket = serverSocket.accept();

                ClientThreadOnServer clientThreadOnServer = new ClientThreadOnServer(this.clientSocket);
                MainApp.executor.submit(clientThreadOnServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
