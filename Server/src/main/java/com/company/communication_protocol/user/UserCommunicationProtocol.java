package com.company.communication_protocol.user;

import com.company.communication_protocol.errors.ServerResponseError;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class UserCommunicationProtocol {

    public static final String COMMAND_SEPARATOR = "&";
    public static final String PARAMETER_SEPARATOR = ";";
    public static final String TERMINATOR = "END";

    @Getter static Socket socket;

    @Getter private static BufferedReader inputStream;
    @Getter private static PrintWriter outputStream;

    @Getter private static UUID id;

    // overriding the default constructor to a private one, so this class can't be instantiated
    private UserCommunicationProtocol(){}

    public static void initialize() {
        initialize(Constants.DEFAULT_IP, Constants.DEFAULT_PORT);
    }

    public static void initialize(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            inputStream  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            initializeId(inputStream.readLine());
        }catch (IOException e){
            System.out.println("multiplayer is not working for you");
        }
    }

    private static void initializeId(String idString) {
        id = UUID.fromString(idString);
    }

    public static void closeConnection() throws IOException {
        outputStream.close();
        inputStream.close();
        socket.close();
    }

    public static String createRoom(String message) throws IOException {
        outputStream.println(CommandType.CREATE + " " + message);
        // server sends back information about the map
        return inputStream.readLine();
    }

    public static String[] listRooms() {
        outputStream.println(CommandType.LIST);
        try {
            return inputStream.readLine().split(";");
        } catch (IOException e) {
            try {
                throw new ServerResponseError("Server didn't respond correctly");
            } catch (ServerResponseError ex) {
                ex.printStackTrace();
            }
        }

        return new String[]{};
    }

    public static String joinRoom(String roomName) throws IOException {
        outputStream.println(CommandType.JOIN + " " + roomName);
        return inputStream.readLine();
    }

}
