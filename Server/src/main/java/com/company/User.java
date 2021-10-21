package com.company;

import com.company.errors.RoomNameException;
import lombok.SneakyThrows;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

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

        boolean terminalInput = false;
        while(!terminalInput) {
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
            PrintWriter creatorWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(currentSocket.getOutputStream())));
            // ObjectOutputStream objectWriter = new ObjectOutputStream(currentSocket.getOutputStream());
            String socketInput = socketReader.readLine();
            String[] commands  = socketInput.split(" ");

            if (commands[0].equals("CREATE")){
                System.out.println("A");
                terminalInput = true;
                createRoom(commands);
            }else {
                System.out.println("B");
                ActiveRooms.getActiveRoomsAsList().forEach(e->{
                    System.out.println(e);
                    creatorWriter.println(e);
                });
//                creatorWriter.println("yos");
                creatorWriter.flush();
                System.out.println("done");
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
