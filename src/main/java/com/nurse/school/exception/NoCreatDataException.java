package com.nurse.school.exception;

public class NoCreatDataException extends RuntimeException{

    public NoCreatDataException() {
        super();
    }

    public NoCreatDataException(String message) {
        super(message);
    }

    public NoCreatDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCreatDataException(Throwable cause) {
        super(cause);
    }
}
