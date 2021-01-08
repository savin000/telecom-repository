package com.savin.di;

/**
 * Exception class of injectable classes repletion
 *
 * @author Mikhail Savin
 * @since 1.0
 */
public class SurplusOfInjectableClassesException extends Exception {

    /**
     * Constructor of the exception with Throwable object
     *
     * @param e the cause of this Throwable
     */
    public SurplusOfInjectableClassesException(Throwable e) {
        initCause(e);
    }

    /**
     * Constructor of the exception with Throwable object and message
     *
     * @param message additional info on exception
     * @param e the exception itself
     */
    public SurplusOfInjectableClassesException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Constructor of the exception with message
     *
     * @param message additional info on exception
     */
    public SurplusOfInjectableClassesException(String message) {
        super(message);
    }
}
