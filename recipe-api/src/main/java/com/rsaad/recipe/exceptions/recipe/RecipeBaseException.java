package com.rsaad.recipe.exceptions.recipe;

public class RecipeBaseException extends RuntimeException{
    private String msg;
    public RecipeBaseException(String msg){
        super(msg);
    }
}
