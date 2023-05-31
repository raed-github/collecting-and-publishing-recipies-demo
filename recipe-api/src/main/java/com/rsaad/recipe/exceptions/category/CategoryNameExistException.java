package com.rsaad.recipe.exceptions.category;

public class CategoryNameExistException extends RuntimeException{
    private String msg;

    public CategoryNameExistException(String msg){
        super(msg);
    }
}
