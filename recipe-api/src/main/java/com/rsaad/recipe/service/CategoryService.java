package com.rsaad.recipe.service;

import com.rsaad.recipe.dto.category.CategoryRequest;
import com.rsaad.recipe.dto.category.CategoryResponse;
import com.rsaad.recipe.dto.recipe.RecipeResponse;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Category findById(Long id);
    public List<Category> findAll();

    public List<Recipe> findCategoryRecipies(Long id);

    public Category saveCategory(Category category);
}
