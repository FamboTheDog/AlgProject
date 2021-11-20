package com.example.backend.exceptions.errors;

public class UserCredentialsError extends RuntimeException {
    public UserCredentialsError() {
        super();
    }
    public UserCredentialsError(String exception) {
        super(exception);
    }
}