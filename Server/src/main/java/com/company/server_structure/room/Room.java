package com.company.server_structure.room;

import com.company.server_structure.ActiveRooms;
import com.company.data.SocketInformation;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Room implements Runnable {

    @Getter private LinkedHashMap<Socket, SocketInformation> players = new LinkedHashMap<>();
    boolean running = true;

    public Room(Socket creatorSocket, String serverName) throws IOException {
        ActiveRooms.addActiveRoom(serverName, this);

        PrintWriter creatorWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(creatorSocket.getOutputStream())), true);
        // creatorWriter.write("150 150");
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
        ArrayList<String> positions = new ArrayList<>();
        final String separator = ";";
        while (running){
            for(Socket key: players.keySet()){
                try {
                    String socketPosition = players.get(key).getReader().readLine();
                    positions.add(socketPosition);
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
            int index = 0;
            for(Socket key: players.keySet()) {
                boolean wrote = false;
                PrintWriter writeTo = players.get(key).getWriter();

                for (int i = 0; i < positions.size() - 1; i++) {
                    if (i == index) continue;
                    writeTo.write(positions.get(i) + separator);
                    wrote = true;
                }
                if (index != positions.size() - 1) {
                    writeTo.write(positions.get(positions.size() - 1));
                    wrote = true;
                }

                if (wrote) writeTo.println();

                index++;
            }

            positions.clear();
        }
        System.out.println("Room stopped");
    }
}
