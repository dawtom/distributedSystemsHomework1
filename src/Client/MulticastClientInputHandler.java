package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClientInputHandler extends Thread {
    private String name;

    public MulticastClientInputHandler(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try (MulticastSocket multicastSocket = new MulticastSocket(15000)) {
            multicastSocket.joinGroup(InetAddress.getByName("224.5.0.1"));
            while (!multicastSocket.isClosed()) {
                // create and receive message on multicast
                byte[] buffer = new byte[500];
                DatagramPacket multicastPacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(multicastPacket);

                String message = new String(buffer);
                if (!message.startsWith(this.name + ":")) {
                    System.out.println("Multicast " + this.name + " sent: " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
