package com.credit.credit.error;

public class NotFoundObject extends RuntimeException {
    public NotFoundObject(String message) {
        super(message);
    }
}
