package com.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class Room implements Runnable {

    private String serverName;
    private HashMap<Socket, SocketInformation> players = new HashMap<>();
    boolean running = true;

    public Room(Socket creatorSocket, String serverName) throws IOException {
        this.serverName = serverName;
        BufferedWriter creatorWriter = new BufferedWriter(new OutputStreamWriter(creatorSocket.getOutputStream()));
        creatorWriter.write("150 150");
        creatorWriter.close();
        SocketInformation creatorInformation = new SocketInformation(new BufferedReader(new InputStreamReader(creatorSocket.getInputStream())), creatorWriter);
        players.put(creatorSocket, creatorInformation); // default location for spawn, will be changed later
    }

    @Override
    public void run() {
        System.out.println("room started");
        while (running){
            players.forEach((socket, info) -> {
                try {
                    System.out.println(info.getReader().readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            players.forEach((socket, info) -> {
                try {
                    info.getWriter().write("test");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
@Data
@AllArgsConstructor
class SocketInformation{
    private BufferedReader reader;
    private BufferedWriter writer;
}