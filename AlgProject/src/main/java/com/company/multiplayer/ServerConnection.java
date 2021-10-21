package com.company.multiplayer;

import com.company.gameloop.MPUpdater;
import lombok.Getter;

import java.io.*;
import java.net.Socket;

final public class ServerConnection {

    static final int defaultPort = 2020;
    static final String defaultIP = "localhost";

    @Getter static Socket socket;

    @Getter private static BufferedReader inputStream;
    @Getter private static PrintWriter outputStream;

    private static boolean alreadyInitialized = false;

    // overriding the default constructor, so this class can't be instantiated
    private ServerConnection(){}

    public static void initialize() {
        initialize(defaultIP, defaultPort);
    }

    public static void initialize(String ip, int port) {
        if (alreadyInitialized) throw new AssertionError("Class is already initialized!");
        alreadyInitialized = true;

        try {
            socket = new Socket(ip, port);
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            inputStream  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            MPUpdater.setSocketWriter(outputStream);
        }catch (IOException e){
            System.out.println("multiplayer is not working for you");
        }
    }

    public static void sendMessage(String message) {
        outputStream.println(message);
    }

}
