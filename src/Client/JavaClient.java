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

        socket.connect(new InetSocketAddress("192.168.0.10", MainApp.portNumber));

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

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            switch (message) {
                case "U": {
                    InetAddress IPAddress = InetAddress.getByName("localhost");
                    datagramSocket.send(new DatagramPacket(car, car.length, IPAddress, MainApp.portNumber));
                    break;
                }
                default:
                    printWriter.write(message + '\n');
                    printWriter.flush();
                    break;
            }
        }

        socket.close();
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = scanner.nextLine();

        System.out.println("JAVA TCP CLIENT");
        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;



        try {
            // create socket
//            socket = new Socket(hostName, portNumber);


            DatagramSocket udpSocket = new DatagramSocket();
//            new UdpClientInputHandler(udpSocket).start();

            // in & out streams
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // send msg, read response

//            out.println(name);
//            out.flush();

//            new TcpClientInputHandler(socket.getInputStream()).start();


            while (true) {
                String message = scanner.nextLine();
//                out.println(message);
                InetAddress address = InetAddress.getByName("localhost");
                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(message.getBytes(),message.getBytes().length, address, portNumber);
                udpSocket.send(sendPacket);

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                socket.close();
            }
        }*/
    }

}
