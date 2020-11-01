package com.savin.contracts;

public class NoBirthDateException extends Exception{
    public NoBirthDateException(Throwable e) {
        initCause(e);
    }
}
