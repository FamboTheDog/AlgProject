package com.company.server_structure.room;

import com.company.data.SocketInformation;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedHashMap;

public class Room implements Runnable {

    @Getter private final LinkedHashMap<Socket, SocketInformation> players = new LinkedHashMap<>();
    private boolean running = true;

    private String serverName;

    public Room(Socket creatorSocket, String serverName) throws IOException {
        this.serverName = serverName;
        ActiveRooms.addActiveRoom(serverName, this);

        PrintWriter creatorWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(creatorSocket.getOutputStream())), true);
        SocketInformation creatorInformation = new SocketInformation(new BufferedReader(new InputStreamReader(creatorSocket.getInputStream())), creatorWriter);
        players.put(creatorSocket, creatorInformation); // default location for spawn, will be changed later
    }

    public void start(){
        Thread informationWriter = new Thread(this);
        informationWriter.start();
        System.out.println("Room started");
    }

    @Override
    public void run() {
        // ArrayList<String> positions = new ArrayList<>();
        while (running){
            for(Socket key: players.keySet()){
                try {
                    String socketPosition = players.get(key).getReader().readLine();

                    for(Socket write: players.keySet()) {
                        if (write == key) continue;

                        PrintWriter writeTo = players.get(write).getWriter();
                        writeTo.write(socketPosition + "\n");
                    }
                } catch (SocketException ex) {
                    System.out.println("player disconnected");
                    players.remove(key);
                    if (players.size() == 0) {
                        running = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(Socket write: players.keySet()) {
                PrintWriter writeTo = players.get(write).getWriter();
                writeTo.println("END");
            }
        }
        ActiveRooms.getActiveRooms().remove(serverName);
        System.out.println("Room stopped");
    }
}
