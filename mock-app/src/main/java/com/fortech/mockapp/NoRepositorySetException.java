package com.fortech.mockapp;

public class NoRepositorySetException extends RuntimeException{
    private String message;

    public NoRepositorySetException(String message) {
        this.message = message;
    }
}
