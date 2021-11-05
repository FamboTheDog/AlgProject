package com.company.communication_protocol.errors;

public class ServerResponseError extends Exception {
    public ServerResponseError(String message){
        super(message);
    }
}
