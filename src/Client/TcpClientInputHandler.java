package Client;

import java.io.InputStream;
import java.util.Scanner;

public class TcpClientInputHandler extends Thread {
    private final Scanner reader;

    public TcpClientInputHandler(InputStream input) {
        this.reader = new Scanner(input);
    }

    @Override
    public void run() {
        while (reader.hasNextLine()) {
            System.out.println("TCP " + reader.nextLine());
        }

    }
}
