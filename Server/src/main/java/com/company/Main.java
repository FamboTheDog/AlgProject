package com.company;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(2020);
        Server server = new Server(socket);
        server.startServer();
    }

}
