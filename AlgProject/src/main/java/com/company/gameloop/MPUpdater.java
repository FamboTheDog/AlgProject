package com.company.gameloop;

import com.company.entities.ConnectedPlayer;
import com.company.entities.RenderObject;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MPUpdater extends Updater implements Runnable {

    @Setter private ArrayList<RenderObject> gameObjects;

    @Getter private final ArrayList<ConnectedPlayer> enemies = new ArrayList<>();

    @Setter private static PrintWriter    socketWriter;
    @Setter private static BufferedReader socketReader;

    @Setter private boolean listening = true;

    @Setter private int playerIdOnServer = 0;

    public void update(){
        // gameObjects.forEach(RenderObject::update);
        for (RenderObject gameObject : gameObjects) {
            gameObject.update();
        }

        String playerPosition = player.getX() + " " + player.getY() + " " + player.getAngle();
        socketWriter.println(playerPosition);
    }

    @SneakyThrows
    @Override
    public void run() {
        // Commands from the server

        while(listening) {
            try {
                String[] positions = socketReader.readLine().split(";");
                Arrays.stream(positions).forEach(e-> System.out.println("this: " + e));

                while (positions.length > enemies.size()) {
                    int newPlayerIndex = positions.length - 1;
                    String[] newPlayerPositions = positions[newPlayerIndex].split(" ");
                    ConnectedPlayer newPlayer = new ConnectedPlayer(Double.parseDouble(newPlayerPositions[0]),
                            Double.parseDouble(newPlayerPositions[1]), Double.parseDouble(newPlayerPositions[2]));
                    enemies.add(newPlayer);
                    gameObjects.add(newPlayer);
                }

                for (int i = 0; i < enemies.size(); i++) {
                    String[] playerPosition = positions[i].split(" ");
                    try{
                        enemies.get(i).setX(Double.parseDouble(playerPosition[0]));
                        enemies.get(i).setY(Double.parseDouble(playerPosition[1]));
                        enemies.get(i).setAngle(Double.parseDouble(playerPosition[2]));
                    } catch(NumberFormatException ex) {
                        System.out.println("error while parsing enemy coordinates");
                    }
                }

                // System.out.println(command + "\n-----");
            } catch (IOException e) {
                listening = false;
                e.printStackTrace();
            }
        }
    }

}
