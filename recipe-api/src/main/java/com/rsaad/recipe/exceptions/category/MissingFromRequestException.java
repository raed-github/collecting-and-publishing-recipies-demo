package com.rsaad.recipe.exceptions.category;

public class MissingFromRequestException extends RuntimeException {
    private String msg;
    public MissingFromRequestException(String msg){
        super(msg);
    }
}
