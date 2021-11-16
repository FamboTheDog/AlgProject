package com.company.libgdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
public class Bullet implements GameObject {

    private float x;
    private float y;

    private final BufferedReader reader = UserCommunicationProtocol.getInputStream();

    @Override
    public void update() {
        try {
            String[] newPos = reader.readLine().split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
            System.out.println(Arrays.toString(newPos));
            x = Float.parseFloat(newPos[1]);
            y = Float.parseFloat(newPos[2]);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(Textures.getBulletTexture(), x, y);
    }
}
