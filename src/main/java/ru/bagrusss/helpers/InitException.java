package ru.bagrusss.helpers;

/**
 * Created by vladislav on 11.01.16.
 */
public class InitException extends Exception {
    public static final String DEFAULT_MSG = "INCORRECT CONFIGS";

    public InitException() {
        super(DEFAULT_MSG);
    }

    public InitException(String message) {
        super(message);
    }
}
