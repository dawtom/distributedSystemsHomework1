package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpClientInputHandler extends Thread {
    private final DatagramSocket udpSocket;

    public UdpClientInputHandler(DatagramSocket datagramSocket) {
        this.udpSocket = datagramSocket;
    }

    @Override
    public void run() {
        try {
            while (!udpSocket.isClosed()) {
                // create packet and buffer
                byte[] buff = new byte[500];
                DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);

                // when receiving message print it
                udpSocket.receive(datagramPacket);
                String message = new String(buff);
                System.out.println("UDP " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            udpSocket.close();
        }
    }
}
