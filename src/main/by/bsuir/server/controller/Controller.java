package main.by.bsuir.server.controller;

import main.by.bsuir.server.controller.CommandProvider;
import main.by.bsuir.server.controller.command.impl.Quit;
import main.by.bsuir.server.controller.command.Command;
import main.by.bsuir.server.controller.exception.ControllerException;

import java.io.*;
import java.net.Socket;

public class Controller extends Thread {
    private final Socket socket;
    private BufferedReader socketReader;
    private PrintWriter socketWriter;

    private final CommandProvider provider = new CommandProvider();
    private final static char delimiter = ' ';

    public Controller(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendResponse("""
                Available commands:
                AUTH login password
                QUIT
                REGISTER login password rights
                ADD firstname lastname
                VIEW
                DELETE firstname lastname
                """);

        boolean running = true;
        do {
            String request = readRequest();
            if (request == null)
                break;
            else
                System.out.println("Receive: " + request);

            String commandName = request;
            Command command;
            if (request.indexOf(delimiter) != -1)
                commandName = request.substring(0, request.indexOf(delimiter));
            command = provider.getCommand(commandName);
            String response = null;
            try {
                response = command.execute(request);
            } catch (ControllerException e) {
                response = e.getMessage();
            }
            sendResponse(response);

            if (command instanceof Quit) {
                running = false;
            }

        } while (running);

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client disconnected");
    }

    public void sendResponse(String response) {
        socketWriter.println(response);
        socketWriter.flush();
    }

    private String readRequest() {
        try {
            return socketReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDelimiter(){
        return String.valueOf(delimiter);
    }
}

