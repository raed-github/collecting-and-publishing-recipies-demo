package com.rsaad.recipe.exceptions;

public class MissingFromRequestException extends RuntimeException {
    private String msg;
    public MissingFromRequestException(String msg){
        super(msg);
    }
}
