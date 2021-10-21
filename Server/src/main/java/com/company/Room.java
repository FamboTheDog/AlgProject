package com.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;

public class Room implements Runnable {

    private String serverName;
    private HashMap<Socket, SocketInformation> players = new HashMap<>();
    boolean running = true;

    public Room(Socket creatorSocket, String serverName) throws IOException {
        this.serverName = serverName;
        PrintWriter creatorWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(creatorSocket.getOutputStream())), true);
        creatorWriter.write("150 150");
        SocketInformation creatorInformation = new SocketInformation(new BufferedReader(new InputStreamReader(creatorSocket.getInputStream())), creatorWriter);
        players.put(creatorSocket, creatorInformation); // default location for spawn, will be changed later
    }

    @Override
    public void run() {
        System.out.println("Room started");
        while (running){
            Iterator<Socket> player = players.keySet().iterator();

            while (player.hasNext()) {
                Socket key = player.next();
                try {
                    System.out.println(players.get(key).getReader().readLine());
                } catch (SocketException ex) {
                    System.out.println("player disconnected");
                    player.remove();
                    if (players.size() == 0) {
                        running = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Room stopped");
    }
}
@Data
@AllArgsConstructor
class SocketInformation{
    // this will later have to hold the username as well
    private BufferedReader reader;
    private PrintWriter writer;
}