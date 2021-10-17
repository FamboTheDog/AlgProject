package com.company;

import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private ServerSocket socket;
    @Setter private boolean running;

    Server(ServerSocket socket) {
        this.socket = socket;
        running = true;
    }

    public void startServer() throws IOException {
        while(running){
            System.out.println("waiting");
            new Thread(new User(socket.accept())).start();
            System.out.println("accepted");
        }
    }

}
