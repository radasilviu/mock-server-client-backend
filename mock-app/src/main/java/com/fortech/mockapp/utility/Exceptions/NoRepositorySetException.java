package com.fortech.mockapp.utility.Exceptions;

public class NoRepositorySetException extends RuntimeException{
    private String message;

    public NoRepositorySetException(String message) {
        this.message = message;
    }
}
