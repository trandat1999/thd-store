package com.thd.store.exception;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
public class StoreException extends RuntimeException {
    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Exception exception) {
        super(message, exception);
    }
}
