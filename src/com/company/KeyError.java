package com.company;

public class KeyError extends Exception {
    KeyError(Throwable e){
        super("Key wasn't found.", e);
    }
}
