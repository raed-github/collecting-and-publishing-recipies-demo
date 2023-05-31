package com.rsaad.recipe.service.impl;

import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.dto.recipe.RecipeCriteria;
import com.rsaad.recipe.exceptions.category.MissingFromRequestException;
import com.rsaad.recipe.exceptions.recipe.RecipeBaseException;
import com.rsaad.recipe.exceptions.recipe.RecipeNameExistException;
import com.rsaad.recipe.exceptions.recipe.RecipeNotFoundException;

import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.repository.CategoryRepository;
import com.rsaad.recipe.repository.RecipeRepository;
import com.rsaad.recipe.search.RecipeSpecificationBuilder;
import com.rsaad.recipe.search.SearchCriteria;
import com.rsaad.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * RecipeServiceImpl class containing the recipe api business logic.
 * @author Raed
 *
 */
@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).map(recipe -> {
            return recipe;
        }).orElseGet(()->{
            throw new RecipeNotFoundException(ApplicationConstants.RECIPE_NOT_FOUND);
        });
    }
    @Override
    public List<Recipe> findAll(RecipeCriteria recipeCriteria,Pageable pageable) {
       if(recipeCriteria.isEmpty())
       {
           return recipeRepository.findAll();
       }
        try {
            RecipeSpecificationBuilder recipeSpecificationBuilder = new RecipeSpecificationBuilder();
            List<SearchCriteria> searchCriteriaList = dtoMapper.recipeToSearchCriteria(recipeCriteria);
            searchCriteriaList.forEach(searchCriteria -> {
                searchCriteria.setDataOption(ApplicationConstants.AND_OPERATION);
                recipeSpecificationBuilder.with(searchCriteria);
            });
            return this.findBySearchCriteria(recipeSpecificationBuilder.build(), pageable);
        }catch (IllegalAccessException e){
            throw new RecipeBaseException(e.getMessage());
        }
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) throws RecipeNameExistException {
        if(recipe.getCategories()==null){
            throw new MissingFromRequestException(ApplicationConstants.CATEGORY_MISSING_FROM_RECIPE_REQUEST);
        }else if(recipe.getIngredients()==null){
            throw new MissingFromRequestException(ApplicationConstants.DIRECTIONS_MISSING_FROM_RECIPE_REQUEST);
        }else if(recipe.getDirections() == null){
            throw new MissingFromRequestException(ApplicationConstants.INGREDIENTS_MISSING_FROM_RECIPE_REQUEST);
        }
        String recipeName = recipe.getName();
        Recipe saveRecipe = recipeRepository.findByName(recipeName);
        if (saveRecipe != null) {
            throw new RecipeNameExistException(ApplicationConstants.RECIPE_NAME_ALREADY_USED);
        }
        return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> findBySearchCriteria(Specification<Recipe> spec, Pageable page) {
        Sort sort = page.getSort();
        List<Recipe> recipes = null;
        if(spec != null) {
            recipes = recipeRepository.findAll(spec, sort);
        }else{
            recipes = recipeRepository.findAll(sort);
        }
        return recipes;
    }
}
