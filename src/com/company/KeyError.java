package com.company;

public class KeyError extends Exception {
    KeyError(Throwable e, String s){
        super("Key wasn't found.", e);
    }
}
