package com.nurse.school.exception;

public class DoesntMatchExcelFormException extends RuntimeException{
    public DoesntMatchExcelFormException() {
        super();
    }

    public DoesntMatchExcelFormException(String message) {
        super(message);
    }

    public DoesntMatchExcelFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoesntMatchExcelFormException(Throwable cause) {
        super(cause);
    }
}
