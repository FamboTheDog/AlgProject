package com.company;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;

public class User implements Runnable{

    private final Socket currentSocket;
    private final UUID id;

    public User(Socket currentSocket) {
        this.currentSocket = currentSocket;
        this.id = UUID.randomUUID();
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("waiting for user");

        //        while() {
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
            String serverName = socketInput.readLine();
        //       }

        Thread newRoom = new Thread(new Room(id, serverName));
        newRoom.start();

        System.out.println("user responded");
    }

}
