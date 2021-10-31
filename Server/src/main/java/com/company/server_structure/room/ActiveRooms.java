package com.company.server_structure.room;

import com.company.data.SocketInformation;
import lombok.Getter;

import java.net.Socket;
import java.util.*;

public class ActiveRooms {

    @Getter private static final HashMap<String, Room> activeRooms = new HashMap<>();

    public static List<String> getActiveRoomsAsList() {
        Set<String> rooms = new HashSet<>(ActiveRooms.activeRooms.keySet());
        if (rooms.isEmpty()) rooms.add("EMPTY");
        return new ArrayList<>(rooms);
    }

    public static void addActiveRoom(String name, Room toAdd) {
        activeRooms.put(name, toAdd);
    }

    public static void addPlayerToActiveRoom(Socket user, String addTo, SocketInformation socketInformation) {
        activeRooms.get(addTo).getPlayers().put(user, socketInformation);
    }

}
