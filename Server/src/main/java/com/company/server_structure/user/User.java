package com.company.server_structure.user;

import com.company.communication_protocol.user.CommandType;
import com.company.data.SocketInformation;
import com.company.errors.RoomNameException;
import com.company.server_structure.room.ActiveRooms;
import com.company.server_structure.room.Room;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
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

    private BufferedReader socketReader;
    private PrintWriter socketWriter;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("waiting for user");
        socketReader = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
        socketWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(currentSocket.getOutputStream())), true);

        SocketInformation socketInformation = new SocketInformation(socketReader, socketWriter);

        ActiveUsers.addPlayer(id, socketInformation);

        boolean terminalInput = false;
        while(!terminalInput) {
            try {
                String socketInput = socketReader.readLine();

                // when socket returns null, it means it was closed
                if (socketInput == null) throw new IOException();
                String[] commands  = socketInput.split(" ");
                CommandType stringToEnum;
                try{
                    stringToEnum = CommandType.valueOf(commands[0]);
                } catch (IllegalArgumentException ex){
                    logger.log(Level.WARNING,
                            "User with ID: " + id + " tried to use a value that doesn't exist: " + commands[0]);
                    continue;
                }
                switch (stringToEnum) {
                    case CREATE -> {
                        terminalInput = true;
                        createRoom(commands);
                    }
                    case LIST ->{
                        ActiveRooms.getActiveRoomsAsList().forEach(e -> {
                            System.out.println(e);
                            socketWriter.write(e + ";");
                        });
                        socketWriter.println();
                    }
                    case JOIN -> {
                        terminalInput = true;
                        ActiveRooms.addPlayerToActiveRoom(currentSocket, commands[1], socketInformation);
                        socketWriter.println("joined");
                        logger.log(Level.INFO, "User joined");
                    }
                    default -> {
                        for (CommandType enumValue: CommandType.values()) {
                            if (enumValue.equals(CommandType.valueOf(commands[0])))
                                logger.log(Level.WARNING, "Server does not have a handler for: " + enumValue);
                        }
                    }
                }
            } catch (IOException e) {
                socketWriter.close();
                socketReader.close();
                currentSocket.close();
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

        socketWriter.println("Room created");
    }

}
