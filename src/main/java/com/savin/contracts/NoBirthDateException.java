package com.savin.contracts;

/**
 * This class is needed when Person instance has no birth date value
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class NoBirthDateException extends Exception{

    /**
     * Creates an exception
     * @param e the cause of this Throwable
     */
    public NoBirthDateException(Throwable e) {
        initCause(e);
    }
}
