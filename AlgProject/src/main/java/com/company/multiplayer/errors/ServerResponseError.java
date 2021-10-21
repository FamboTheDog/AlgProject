package com.company.multiplayer.errors;

public class ServerResponseError extends Exception {
    public ServerResponseError(String message){
        super(message);
    }
}
