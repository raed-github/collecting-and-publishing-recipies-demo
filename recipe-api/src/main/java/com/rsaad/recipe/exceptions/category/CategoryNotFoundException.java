package com.rsaad.recipe.exceptions.category;

public class CategoryNotFoundException extends RuntimeException{
    private String msg;
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
