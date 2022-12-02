package main.by.bsuir.client;

import main.by.bsuir.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    private boolean running;
    private PrintWriter socketWriter;

    public Client() {
    }

    @Override
    public void run() {
        running = true;
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), Server.PORT);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            ClientReader clientReader = new ClientReader(this);
            clientReader.start();

            String response;
            while ((response = socketReader.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public PrintWriter getSocketWriter() {
        return socketWriter;
    }
}
