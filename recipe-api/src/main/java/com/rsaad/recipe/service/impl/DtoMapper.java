package com.rsaad.recipe.service.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.controller.CategoryController;
import com.rsaad.recipe.controller.RecipeController;
import com.rsaad.recipe.dto.category.CategoryRequest;
import com.rsaad.recipe.dto.category.CategoryResponse;
import com.rsaad.recipe.dto.direction.DirectionRequest;
import com.rsaad.recipe.dto.direction.DirectionResponse;
import com.rsaad.recipe.dto.ingredient.IngredientRequest;
import com.rsaad.recipe.dto.ingredient.IngredientResponse;
import com.rsaad.recipe.dto.recipe.RecipeCriteria;
import com.rsaad.recipe.dto.recipe.RecipeRequest;
import com.rsaad.recipe.dto.recipe.RecipeResponse;
import com.rsaad.recipe.model.*;
import com.rsaad.recipe.search.SearchCriteria;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DtoMapper class which is responsible for Dto pattern mapping between models and dto objects.
 * @author Raed Saad
 *
 */
@Service
public class DtoMapper {
    public Recipe toRecipe(RecipeRequest recipeRequest){
        Set<Ingredient> ingredientSet = recipeRequest.getIngredients()!=null?recipeRequest.getIngredients().stream()
                .map(this::toIngredient).collect(Collectors.toSet()):null;
        Set<Category> categorySet = recipeRequest.getCategories()!=null?recipeRequest.getCategories().stream()
                .map(this::toCategory).collect(Collectors.toSet()):null;
        Set<Direction> directionSet = recipeRequest.getDirections()!=null?recipeRequest.getDirections().stream()
                .map(this::toDirection).collect(Collectors.toSet()):null;
        return Recipe.builder()
                .name(recipeRequest.getName())
                .yield(recipeRequest.getYield())
                .serves(recipeRequest.getServes())
                .cookingTime(recipeRequest.getCookingTime())
                .preparationTime(recipeRequest.getPreparationTime())
                .difficultyLevel(recipeRequest.getDifficultyLevel())
                .categories(categorySet)
                .ingredients(ingredientSet)
                .directions(directionSet)
                .build();
    }

    public RecipeResponse toRecipeResponce(Recipe recipe){
        Link selfLink = linkTo(methodOn(RecipeController.class)
                .findRecipeById(recipe.getId())).withSelfRel();
        Set<IngredientResponse> ingredientSet = recipe.getIngredients().stream()
                .map(this::toIngredientResponse).collect(Collectors.toSet());
        Set<CategoryResponse> categorySet = recipe.getCategories().stream()
                .map(this::toCategoryResponse).collect(Collectors.toSet());
        Set<DirectionResponse> directionSet = recipe.getDirections().stream()
                .map(this::toDirectionResponse).collect(Collectors.toSet());
        return (RecipeResponse.builder()
                .name(recipe.getName())
                .yield(recipe.getYield())
                .serves(recipe.getServes())
                .cookingTime(recipe.getCookingTime())
                .preparationTime(recipe.getPreparationTime())
                .difficultyLevel(recipe.getDifficultyLevel())
                .categories(categorySet)
                .ingredients(ingredientSet)
                .directions(directionSet)
                .build()).add(selfLink);
    }
    public Category toCategory(CategoryRequest categoryRequest){
        return Category.builder().id(categoryRequest.getId()).category(categoryRequest.getCategory()).build();
    }

    public CategoryResponse toCategoryResponse(Category category){
        Link selfLink = linkTo(methodOn(CategoryController.class)
                .findCategoryById(category.getId())).withSelfRel();
        Link recipiesLink = linkTo(methodOn(CategoryController.class)
                .findCategoryRecipies(category.getId())).withRel(ApplicationConstants.WITH_REL_RECIPIES);
        return (CategoryResponse.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build())
                .add(selfLink)
                .add(recipiesLink);
    }
    public Direction toDirection(DirectionRequest directionRequest){
        return Direction.builder().step(directionRequest.getStep()).build();
    }

    public DirectionResponse toDirectionResponse(Direction direction){
        return DirectionResponse.builder().step(direction.getStep()).build();
    }
    public Ingredient toIngredient(IngredientRequest ingredientRequest){
        return Ingredient.builder().ingredient(ingredientRequest.getIngredient())
                .quantity(ingredientRequest.getQuantity())
                .unit(ingredientRequest.getUnit())
                .build();
    }

    public IngredientResponse toIngredientResponse(Ingredient ingredient){
        return IngredientResponse.builder().ingredient(ingredient.getIngredient())
                .quantity(ingredient.getQuantity())
                .unit(ingredient.getUnit()).build();
    }

    public RecipeRequest toRecipeRequest(Recipe recipe){
        return RecipeRequest.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .yield(recipe.getYield())
                .difficultyLevel(recipe.getDifficultyLevel())
                .cookingTime(recipe.getCookingTime())
                .preparationTime(recipe.getPreparationTime())
                .serves(recipe.getServes())
                .categories(recipe.getCategories()!=null?recipe.getCategories().stream().map(this::toCategoryRequest).collect(Collectors.toSet()):null)
                .ingredients(recipe.getIngredients()!=null?recipe.getIngredients().stream().map(this::toIngredientRequest).collect(Collectors.toSet()):null)
                .directions(recipe.getDirections()!=null?recipe.getDirections().stream().map(this::toDirectionRequest).collect(Collectors.toSet()):null)
                .build();
    }

    public CategoryRequest toCategoryRequest(Category category){
        return CategoryRequest.builder()
                .id(category.getId())
                .category(category.getCategory()).build();
    }

    public IngredientRequest toIngredientRequest(Ingredient ingredient){
        return IngredientRequest.builder()
                .ingredient(ingredient.getIngredient())
                .quantity(ingredient.getQuantity())
                .unit(ingredient.getUnit()).build();
    }

    public DirectionRequest toDirectionRequest(Direction direction){
        return DirectionRequest.builder()
                .step(direction.getStep())
                .build();
    }
    public List<SearchCriteria> recipeToSearchCriteria(RecipeCriteria recipeCriteria) throws IllegalAccessException {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        Field[] fields = RecipeCriteria.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.get(recipeCriteria)!=null) {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.setFilterKey(field.getName());
                searchCriteria.setValue(field.get(recipeCriteria));
                searchCriteria.setOperation("eq");
                searchCriteria.setDataOption("all");
                searchCriteriaList.add(searchCriteria);
            }
        }
        return searchCriteriaList;
    }
}
