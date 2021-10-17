package com.company;

import java.util.HashMap;
import java.util.UUID;

public class Room implements Runnable {

    private String serverName;
    private UUID creatorId;
    private HashMap<UUID, double[]> players = new HashMap<>();
    boolean running = true;

    public Room(UUID creatorId, String serverName) {
        this.creatorId = creatorId;
        this.serverName = serverName;
        players.put(creatorId, new double[]{150, 150}); // default location for spawn, will be changed later
    }

    @Override
    public void run() {
        System.out.println("room started by " + creatorId);
        while (running){

        }
    }
}
