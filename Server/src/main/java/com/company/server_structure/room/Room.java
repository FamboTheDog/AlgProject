package com.company.server_structure.room;

import com.badlogic.gdx.math.Vector2;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.data.UserCommunication;
import com.company.data.UserInformation;
import com.company.server_structure.room.entities.Asteroids;
import com.company.server_structure.room.entities.Bullet;
import lombok.Getter;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Runnable {

    private boolean running = true;

    @Getter private final LinkedHashMap<Socket, UserInformation> players = new LinkedHashMap<>();
    @Getter private final StringBuilder positions;

    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private final String serverName;

    private final Logger logger = Logger.getLogger(Room.class.getName());

    @Getter private static final String DEFAULT_POSITION = "150;400;0"; // default location for spawn, WILL  be changed later

    public Room(Socket creatorSocket, UserCommunication creatorData, String serverName) {
        this.serverName = serverName;
        ActiveRooms.addActiveRoom(serverName, this);

        UserInformation creatorInformation = new UserInformation(creatorData, 400f, 150f, 0f, System.currentTimeMillis());
        players.put(creatorSocket, creatorInformation);

        positions = new StringBuilder();
        positions.append(DEFAULT_POSITION + UserCommunicationProtocol.COMMAND_SEPARATOR);

        Random rng = new Random();
        for (int i = 0; i < rng.nextInt(3) + 3; i++) {
            positions.append(Asteroids.createAsteroid()).append(UserCommunicationProtocol.PARAMETER_SEPARATOR);
        }
        positions.deleteCharAt(positions.length() - 1);
        creatorData.getWriter().println(positions);
    }

    public void start(){
        Thread informationWriter = new Thread(this);
        informationWriter.start();
        logger.log(Level.INFO, "Room started");
    }

    private static final int MOVE_SPEED = 3;
    private final Vector2 speedV = new Vector2(0, MOVE_SPEED);
    private float lastRotation = 0;

    @Override
    public void run() {
        while (running){
            Optional<Socket> toRemove = Optional.empty();
            for(Socket key: players.keySet()){
                try {
                    String userInput = players.get(key).getUserCommunication().getReader().readLine();
                    String[] userMoves = userInput.split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
                    UserInformation movingUser = players.get(key);

                    parseUserMoves(userMoves, movingUser, key);
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
                    players.forEach((write, writePos) -> {
                            players.get(write).getUserCommunication().getWriter().
                                    write(readPos.getX() + " " + readPos.getY() + " " + readPos.getAngle() + "\n");
                    }
            ));

            StringBuilder bulletBuilder = new StringBuilder();
            for (Bullet bullet : bullets) {
                bulletBuilder
                        .append("BULLET" + UserCommunicationProtocol.PARAMETER_SEPARATOR)
                        .append(bullet.getX()).append(UserCommunicationProtocol.PARAMETER_SEPARATOR)
                        .append(bullet.getY()).append("\n");
            }
            String bulletParameter = bulletBuilder.toString();

            players.forEach((player, pos) -> pos.getUserCommunication().getWriter().
                    println(bulletParameter + UserCommunicationProtocol.TERMINATOR));

            bullets.forEach(Bullet::update);
        }
        ActiveRooms.getActiveRooms().remove(serverName);
        logger.log(Level.INFO, "Room stopped");
    }

    private void parseUserMoves(String[] userMoves, UserInformation movingUser, Socket key) {
        // if the player has more than 4 commands, either an update was made or he is hacking in some way, or there is some glitch
        final int MAX_COMMANDS = 4;
        for (int i = 0; i < userMoves.length && i < MAX_COMMANDS; i++) {
            switch (userMoves[i]) {
                case "LEFT" -> movingUser.setAngle(movingUser.getAngle() + 5f);
                case "RIGHT" -> movingUser.setAngle(movingUser.getAngle() - 5f);
                case "FORWARD" -> {
                    // speedV.set(0, MOVE_SPEED);
                    speedV.rotate(-lastRotation);
                    speedV.rotate(movingUser.getAngle());
                    lastRotation = movingUser.getAngle();
                    movingUser.setX(movingUser.getX() + speedV.x);
                    movingUser.setY(movingUser.getY() + speedV.y);
                    // speedV.set(0, 0);
                }
                case "SHOOT" -> {
                    shoot(key, movingUser);
                    System.out.println(movingUser.getAngle());
                }
            }
        }
        System.out.println(movingUser.getAngle());
    }

    private void shoot(Socket key, UserInformation userInformation) {
        long now = System.currentTimeMillis();
        System.out.println(now - userInformation.getLastTimeShot());
        if (now - userInformation.getLastTimeShot() < 100) return;
        userInformation.setLastTimeShot(now);
        Bullet bullet = new Bullet(key, userInformation);
        this.bullets.add(bullet);
    }
}
