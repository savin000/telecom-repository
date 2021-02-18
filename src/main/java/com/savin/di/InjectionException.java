package com.savin.di;

/**
 * Exception class of non-injectable classes
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class InjectionException extends Exception{

    /**
     * Constructor of the exception with Throwable object and message
     *
     * @param message additional info on exception
     * @param e the exception itself
     */
    public InjectionException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Constructor of the exception with message
     *
     * @param message additional info on exception
     */
    public InjectionException(String message) {
        super(message);
    }
}
