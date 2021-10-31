package com.company.server_structure.user;

import com.company.data.SocketInformation;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class ActiveUsers {

    @Getter private static final HashMap<UUID, SocketInformation> activePlayers = new HashMap<>();

    public static void addPlayer(UUID id, SocketInformation user) {
        activePlayers.put(id, user);
    }

}
