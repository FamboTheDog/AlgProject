package com.company.server_structure.room.entities;

import com.badlogic.gdx.math.Vector2;
import com.company.communication_protocol.PlayerSize;
import com.company.data.UserInformation;
import lombok.Getter;

import java.net.Socket;

public class Bullet {

    private final Socket owner;

    @Getter private float x;
    @Getter private float y;

    private final Vector2 movement;

    public Bullet(Socket key, UserInformation userInformation) {
        this.owner = key;
        this.x = userInformation.getX() + (PlayerSize.PLAYER_SIZE / 4);
        this.y = userInformation.getY();

        int DEFAULT_SPEED = 5;
        this.movement = new Vector2(0, DEFAULT_SPEED);
        movement.rotate(userInformation.getAngle());
    }

    public void update() {
        this.x = x + movement.x;
        this.y = y + movement.y;
    }

}
