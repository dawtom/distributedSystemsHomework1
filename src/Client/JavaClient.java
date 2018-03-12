package Client;

import Server.MainApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class JavaClient {
    private static byte[] car;

    public static void main(String[] args) throws IOException {



        System.out.println("Enter name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        Socket socket = new Socket();

//        socket.connect(new InetSocketAddress("192.168.0.10", MainApp.portNumber));
        socket.connect(new InetSocketAddress("localhost", MainApp.portNumber));

        DatagramSocket datagramSocket = new DatagramSocket(socket.getLocalPort());

        new UdpClientInputHandler(datagramSocket).start();


        car = (name + "                   .---;-,\n" +
                "                __/_,{)|__;._                 \n" +
                "             .\"` _     :  _  `.  .:::;.    .::'\n" +
                "         jgs '--(_)------(_)--' `      '::'").getBytes();

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

        // get name of user
        printWriter.write(name + '\n');
        printWriter.flush();


        new TcpClientInputHandler(socket.getInputStream()).start();
        new MulticastClientInputHandler(name).start();

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            switch (message) {
                case "U": {
//                    InetAddress IPAddress = InetAddress.getByName("192.168.0.10");
                    InetAddress IPAddress = InetAddress.getByName("localhost");
                    datagramSocket.send(new DatagramPacket(car, car.length, IPAddress, MainApp.portNumber));
                    break;
                }
                case "N": {
                    InetAddress IPAddress = InetAddress.getByName("224.5.0.1");
                    datagramSocket.send(new DatagramPacket(car, car.length, IPAddress, 15000));
                    break;
                }
                default:
                    printWriter.write(message + '\n');
                    printWriter.flush();
                    break;
            }
        }

        socket.close();

    }

}
