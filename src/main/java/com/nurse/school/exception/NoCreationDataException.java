package com.nurse.school.exception;

public class NoCreationDataException extends RuntimeException{

    public NoCreationDataException() {
        super();
    }

    public NoCreationDataException(String message) {
        super(message);
    }

    public NoCreationDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCreationDataException(Throwable cause) {
        super(cause);
    }
}
