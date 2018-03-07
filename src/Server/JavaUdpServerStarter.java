package Server;

import sun.applet.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class JavaUdpServerStarter extends Thread {


    @Override
    public void run() {
        try (DatagramSocket udpSocket = new DatagramSocket(MainApp.portNumber)) {
            //unless is closed
            while (!udpSocket.isClosed()) {
                //create buffer and packet based on it
                byte[] buffer = new byte[500];
                DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length);

                // receive message
                udpSocket.receive(udpPacket);
                // create string based on buffer and split it into nick and message
                String message = new String(buffer);
                System.out.println("received: " + message);

                String[] split = message.split(":", 2);
                String nick = split[0];

                // protecting the threads map
                synchronized (MainApp.clientsThreadsMap) {
                    // for each client thread that is not me
                    MainApp.clientsThreadsMap.entrySet().stream().filter(a -> !a.getKey().equals(nick))
                            .forEach(a -> {
                                try {
                                    // get port from client
                                    int port = a.getValue().getClientSocket().getPort();
                                    InetAddress IPAddress = InetAddress.getByName("localhost");
                                    byte[] newBytes = (message).getBytes();
                                    // send message to client
                                    udpSocket.send(new DatagramPacket(newBytes, newBytes.length, IPAddress, port));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
