package com.company.gameloop;

import com.company.entities.RenderObject;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MPUpdater extends Updater{

    @Setter private ArrayList<RenderObject> gameObjects;

    @Setter private static PrintWriter socketWriter;

    public void update(){
        gameObjects.forEach(RenderObject::update);
        String playerPosition = player.getX() + " " + player.getY() + " " + player.getAngle();
        socketWriter.println(playerPosition);
    }
}
