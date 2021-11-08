package com.company.server_structure.user;

import com.company.data.UserInformation;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class ActiveUsers {

    @Getter private static final HashMap<UUID, UserInformation> activePlayers = new HashMap<>();

    public static void addPlayer(UUID id, UserInformation user) {
        activePlayers.put(id, user);
    }

}
