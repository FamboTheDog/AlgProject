package com.company.server_structure.user;

import com.company.data.UserCommunication;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

public class ActiveUsers {

    @Getter @Setter private static HashMap<UUID, UserCommunication> activePlayers = new HashMap<>();

    public static void addPlayer(UUID id, UserCommunication user) {
        activePlayers.put(id, user);
    }

    public static void removePlayer(UUID id) {
        activePlayers.remove(id);
    }
}
