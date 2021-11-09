package com.company.server_structure.room;

import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.data.UserCommunication;
import com.company.data.UserInformation;
import com.company.server_structure.room.entities.Asteroids;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Runnable {

    @Getter private final LinkedHashMap<Socket, UserInformation> players = new LinkedHashMap<>();
    private boolean running = true;

    private final String serverName;

    @Getter private final StringBuilder positions;

    private final Logger logger = Logger.getLogger(Room.class.getName());

    @Getter private static final String DEFAULT_POSITION = "150;400;0"; // default location for spawn, will probably be changed later

    public Room(Socket creatorSocket, UserCommunication creatorData, String serverName) throws IOException {
        this.serverName = serverName;
        ActiveRooms.addActiveRoom(serverName, this);

        UserInformation creatorInformation = new UserInformation(creatorData, 400f, 150f, 0f);
        players.put(creatorSocket, creatorInformation);

        positions = new StringBuilder();
        positions.append(DEFAULT_POSITION + UserCommunicationProtocol.commandSeparator);

        Random rng = new Random();
        for (int i = 0; i < rng.nextInt(3) + 3; i++) {
            positions.append(Asteroids.createAsteroid()).append(UserCommunicationProtocol.parameterSeparator);
        }
        positions.deleteCharAt(positions.length() - 1);
        System.out.println(positions);
        creatorData.getWriter().println(positions);
    }

    public void start(){
        Thread informationWriter = new Thread(this);
        informationWriter.start();
        logger.log(Level.INFO, "Room started");
    }

    @Override
    public void run() {
        while (running){
            Optional<Socket> toRemove = Optional.empty();
            for(Socket key: players.keySet()){
                try {
                    String userInput = players.get(key).getUserCommunication().getReader().readLine();
                    String[] userMoves = userInput.split(";");
                    UserInformation movingUser = players.get(key);

                    // if the player has more than 4 commands, either an update was made or he is hacking in some way
                    for (int i = 0; i < 3 && i < userMoves.length; i++) {
                        switch (userMoves[i]) {
                            case "LEFT" -> movingUser.setAngle(movingUser.getAngle() + 5f);
                            case "RIGHT" -> movingUser.setAngle(movingUser.getAngle() - 5f);
                            case "FORWARD" -> {
                                // movingUser.setX(movingUser.getX() + movingUser.getAngle());
                                // movingUser.setY(movingUser.getY() + movingUser.getAngle());
                            }
                            // how the hell will I do this on the server without vectors
                        }
                    }

//                    for(Socket write: players.keySet()) {
//                        // if (write == key) continue;
//
//                        PrintWriter writeTo = players.get(write).getUserCommunication().getWriter();
//                        System.out.println(movingUser.getX() + " " +  movingUser.getY() + " " + movingUser.getAngle());
//                        writeTo.write(movingUser.getX() + " " +  movingUser.getY() + " " + movingUser.getAngle() + "\n");
//                    }
                } catch (SocketException ex) {
                    System.out.println("player disconnected");
                    toRemove = Optional.of(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (toRemove.isPresent()) {
                players.remove(toRemove.get());
                if (players.size() == 0) running = false;
            }

            players.forEach((read, readPos) ->
                    players.forEach((write, writePos) ->
                        players.get(write).getUserCommunication().getWriter().write(readPos.getX() + " " + readPos.getY() + " " + readPos.getAngle() + "\n")));

            players.forEach((player, pos) -> pos.getUserCommunication().getWriter().println("END"));
//            for(Socket write: players.keySet()) {
//                PrintWriter writeTo = players.get(write).getUserCommunication().getWriter();
//                writeTo.println("END");
//            }
        }
        ActiveRooms.getActiveRooms().remove(serverName);
        logger.log(Level.INFO, "Room stopped");
    }
}
