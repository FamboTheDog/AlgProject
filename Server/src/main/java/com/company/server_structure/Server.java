package com.company.server_structure;

import com.company.server_structure.user.ActiveUsers;
import com.company.server_structure.user.UserHandler;
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
            System.out.println(ActiveUsers.getActivePlayers().size());
            new Thread(new UserHandler(socket.accept())).start();
            System.out.println("accepted");
        }
    }

}
