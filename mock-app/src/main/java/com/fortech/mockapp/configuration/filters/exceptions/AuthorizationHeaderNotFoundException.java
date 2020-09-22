package com.fortech.mockapp.configuration.filters.exceptions;

import java.io.IOException;

public class AuthorizationHeaderNotFoundException extends IOException {
    public AuthorizationHeaderNotFoundException(String message) {
        super(message);
    }
}
