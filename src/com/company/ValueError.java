package com.company;

public class ValueError extends Exception{
    ValueError(Throwable e, String description){
        super(description, e);
    }
}
