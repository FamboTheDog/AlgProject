package com.company.server_structure.room;

import com.company.data.UserInformation;
import com.company.server_structure.room.entities.Asteroids;
import com.company.server_structure.user.UserHandler;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Runnable {

    @Getter private final LinkedHashMap<Socket, UserInformation> players = new LinkedHashMap<>();
    private boolean running = true;

    private String serverName;

    @Getter private final StringBuilder asteroidsPositions;

    private final Logger logger = Logger.getLogger(Room.class.getName());

    public Room(Socket creatorSocket, UserInformation creatorData, String serverName) throws IOException {
        this.serverName = serverName;
        ActiveRooms.addActiveRoom(serverName, this);

        players.put(creatorSocket, creatorData); // default location for spawn, will be changed later

        Random rng = new Random();
        asteroidsPositions = new StringBuilder();
        for (int i = 0; i < rng.nextInt(3,6); i++) {
            asteroidsPositions.append(Asteroids.createAsteroid()).append(";");
        }
        asteroidsPositions.deleteCharAt(asteroidsPositions.length() - 1);
        System.out.println(asteroidsPositions);
        creatorData.getWriter().println(asteroidsPositions);
    }

    public void start(){
        Thread informationWriter = new Thread(this);
        informationWriter.start();
        logger.log(Level.INFO, "Room started");
    }

    @Override
    public void run() {
        while (running){
            Optional<Socket> toRemove = Optional.empty();
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
                    toRemove = Optional.of(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (toRemove.isPresent()) {
                players.remove(toRemove.get());
                if (players.size() == 0) running = false;
            }
            for(Socket write: players.keySet()) {
                PrintWriter writeTo = players.get(write).getWriter();
                writeTo.println("END");
            }
        }
        ActiveRooms.getActiveRooms().remove(serverName);
        logger.log(Level.INFO, "Room started");
    }
}
