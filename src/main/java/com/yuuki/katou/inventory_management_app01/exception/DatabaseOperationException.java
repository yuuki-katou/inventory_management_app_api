package com.yuuki.katou.inventory_management_app01.exception;


public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException() {
        super();
    }

    public DatabaseOperationException(String operation, String message) {
        super(operation + " failed: " + message);
    }
}
