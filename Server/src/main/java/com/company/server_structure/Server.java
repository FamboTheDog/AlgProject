package com.company.server_structure;

import com.company.server_structure.user.User;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final ServerSocket socket;
    @Setter private boolean running;

    public Server(ServerSocket socket) {
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
