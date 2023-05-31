package com.rsaad.recipe.exceptions.recipe;

public class RecipeNotFoundException extends RuntimeException{
    private String msg;

    public RecipeNotFoundException(String msg){
        super(msg);
    }
}
