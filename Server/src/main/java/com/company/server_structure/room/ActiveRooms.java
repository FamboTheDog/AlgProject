package com.company.server_structure.room;

import com.company.data.UserCommunication;
import com.company.data.UserInformation;
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

    public static void addPlayerToActiveRoom(Socket user, String addTo, UserCommunication userCommunication) {
        activeRooms.get(addTo).getPlayers().put(user, new UserInformation(userCommunication, 400f, 150f, 0f, System.currentTimeMillis()));
    }

    public static Room getActiveRoomByName(String name) {
        return activeRooms.get(name);
    }

}
