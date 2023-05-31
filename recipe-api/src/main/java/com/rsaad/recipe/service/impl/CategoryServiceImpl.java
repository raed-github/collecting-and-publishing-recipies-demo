package com.rsaad.recipe.service.impl;

import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.exceptions.category.CategoryNameExistException;
import com.rsaad.recipe.exceptions.category.CategoryNotFoundException;
import com.rsaad.recipe.exceptions.recipe.RecipeNotFoundException;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.repository.CategoryRepository;
import com.rsaad.recipe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CategoryServiceImpl class which contains all business logic of a category.
 * @author Raed
 *
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public Category saveCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategory(category.getCategory());
        if(savedCategory!=null){
            throw new CategoryNameExistException(ApplicationConstants.CATEGORY_NAME_ALREADY_USED);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> category).orElseGet(()->{
            throw new CategoryNotFoundException(ApplicationConstants.CATEGORY_NOT_FOUND);
        });
    }
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Recipe> findCategoryRecipies(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.map(savedCategory -> {
            if(savedCategory.getRecipes().size()>0)
                return savedCategory.getRecipes().stream().toList();
            throw new RecipeNotFoundException(ApplicationConstants.RECIPE_NOT_FOUND);
        }).orElseGet(() -> {
            throw new CategoryNotFoundException(ApplicationConstants.CATEGORY_NOT_FOUND);
        });
    }
}
