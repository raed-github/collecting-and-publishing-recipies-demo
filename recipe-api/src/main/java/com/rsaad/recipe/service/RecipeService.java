package com.rsaad.recipe.service;

import com.rsaad.recipe.dto.recipe.RecipeCriteria;

import com.rsaad.recipe.exceptions.recipe.RecipeNameExistException;
import com.rsaad.recipe.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RecipeService {

    public Recipe findRecipeById(Long id);
    public List<Recipe> findAll(RecipeCriteria recipeCriteria,Pageable pageable) throws RecipeNameExistException;
    public Recipe saveRecipe(Recipe recipe);

}
