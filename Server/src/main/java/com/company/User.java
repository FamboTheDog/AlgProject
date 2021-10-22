package com.company;

import com.company.data.SocketInformation;
import com.company.errors.RoomNameException;
import com.company.logger.LoggerUtil;
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

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("waiting for user");
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(currentSocket.getOutputStream())));
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
                            socketWriter.println(e);
                        });
                        socketWriter.flush();
                    }
                    case "JOIN" -> {
                        terminalInput = true;
                        LoggerUtil.getLogger().log(Level.INFO, "User is trying to join");

                        ActiveRooms.addPlayerToActiveRoom(currentSocket, commands[1], socketInformation);
                        LoggerUtil.getLogger().log(Level.INFO, "User joined");
                    }
                    default -> LoggerUtil.getLogger().log(Level.WARNING,
                            "User with id: " + id + " send a bad command.");
                }
            } catch (SocketException e){
                LoggerUtil.getLogger().log(Level.INFO, "User with id: " + id + " disconnected.");
                terminalInput = true;
            }
        }

        System.out.println("user responded");
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
            Thread newRoom = new Thread(new Room(currentSocket, serverName));
            newRoom.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
