package com.company.gameloop;

import com.company.entities.OtherPlayer;
import com.company.entities.RenderObject;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Updater {

    @Setter private ArrayList<RenderObject> gameObjects;

    @Setter private static BufferedReader socketReader;

    public void update(){
        // if concurrent modification happens in this loop, it should be replaced with a classic index based loop
        for (RenderObject gameObject : gameObjects) {
            gameObject.update();
        }

        String line;
        try {
            while (!(line = socketReader.readLine()).equals("END")) {
                String[] newPlayerPositions = line.split(" ");
                OtherPlayer newPlayer = new OtherPlayer(Double.parseDouble(newPlayerPositions[0]),
                        Double.parseDouble(newPlayerPositions[1]), Double.parseDouble(newPlayerPositions[2]), socketReader);
                gameObjects.add(newPlayer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

