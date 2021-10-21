package com.company;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ActiveRooms {

    static HashMap<String, Room> activeRooms;

    static{
        activeRooms = new HashMap<>();
        try {
            activeRooms.put("TestServer", new Room(new Socket("localhost", 2020), "Name"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getActiveRoomsAsList() {
        Set<String> rooms = new HashSet<>(ActiveRooms.activeRooms.keySet());
        if (rooms.isEmpty()) rooms.add("EMPTY");
        return new ArrayList<>(rooms);
    }

    public static void addActiveRoom(String name, Room toAdd) {
        activeRooms.put(name, toAdd);
    }

}
