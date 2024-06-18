package com.example.AuthenticationProject.exception;

import lombok.NoArgsConstructor;

public class ConflictException extends RuntimeException {

    public ConflictException() {

    }

    public ConflictException(String message) {
        super(message);
    }
}
