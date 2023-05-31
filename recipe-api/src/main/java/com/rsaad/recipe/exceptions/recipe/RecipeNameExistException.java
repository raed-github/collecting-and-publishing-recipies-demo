package com.rsaad.recipe.exceptions.recipe;

public class RecipeNameExistException extends RuntimeException{
    private String msg;
    public RecipeNameExistException(String msg){
        super(msg);
    }
}
