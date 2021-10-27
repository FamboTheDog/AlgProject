package com.company.server_structure;

import com.company.data.SocketInformation;
import com.company.errors.RoomNameException;
import com.company.server_structure.room.Room;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User implements Runnable{

    private final Socket currentSocket;
    private final UUID id;

    public User(Socket currentSocket) {
        this.currentSocket = currentSocket;
        this.id = UUID.randomUUID();
    }

    private final Logger logger = Logger.getLogger(User.class.getName());

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("waiting for user");
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(currentSocket.getOutputStream())), true);
        // ObjectOutputStream objectWriter = new ObjectOutputStream(currentSocket.getOutputStream());

        SocketInformation socketInformation = new SocketInformation(socketReader, socketWriter);

        boolean terminalInput = false;
        while(!terminalInput) {
            try {
                String socketInput = socketReader.readLine();
                String[] commands  = socketInput.split(" ");

                switch (commands[0]) {
                    case "CREATE" -> {
                        terminalInput = true;
                        createRoom(commands);
                    }
                    case "LIST" ->{
                        ActiveRooms.getActiveRoomsAsList().forEach(e -> {
                            System.out.println(e);
                            socketWriter.write(e + ";");
                        });
                        socketWriter.println();
                    }
                    case "JOIN" -> {
                        terminalInput = true;
                        ActiveRooms.addPlayerToActiveRoom(currentSocket, commands[1], socketInformation);
                        logger.log(Level.INFO, "User joined");
                    }
                    default -> logger.log(Level.WARNING,
                            "User with id: " + id + " sent a bad command.");
                }
            } catch (SocketException e){
                logger.log(Level.INFO, "User with id: " + id + " disconnected.");
                terminalInput = true;
            }
        }
    }

    private void createRoom(String[] commands) {
        String serverName = "Default_name"; // TEMP: these names should be unique in product code

        if (commands.length < 2) {
            try{
                throw new RoomNameException("Room name was not supplied!");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            serverName = commands[1];
        }

        try {
            Room room = new Room(currentSocket, serverName);
            room.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
