package com.rsaad.recipe.controller;

import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.dto.recipe.RecipeCriteria;
import com.rsaad.recipe.dto.recipe.RecipeRequest;
import com.rsaad.recipe.dto.recipe.RecipeResponse;
import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.service.RecipeService;
import com.rsaad.recipe.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Recipe Controller Class exposed.
 * @author Raed
 *
 */
@RestController
@RequestMapping("/api/v1/recipies")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private DtoMapper dtoMapper;

    @GetMapping
    public ResponseEntity findAllRecipies(RecipeCriteria recipeCriteria,
                                          @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = ApplicationConstants.DEFAULT_SORT_FIELD, direction = Sort.Direction.ASC)
            })
            Pageable pageable
            ){
        List<Recipe> savedRecipies = recipeService.findAll(recipeCriteria,pageable);
        List<RecipeResponse> recipeResponseList = savedRecipies.stream().map(dtoMapper::toRecipeResponce).toList();
        return new ResponseEntity<>(recipeResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findRecipeById(@PathVariable("id") Long id){
        Recipe recipe = recipeService.findRecipeById(id);
        RecipeResponse recipeResponse = dtoMapper.toRecipeResponce(recipe);
        return new ResponseEntity(recipeResponse.removeLinks(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity saveRecipe(@Valid @RequestBody RecipeRequest recipeRequest){
         Recipe recipe = dtoMapper.toRecipe(recipeRequest);
         Recipe savedRecipe = recipeService.saveRecipe(recipe);
         RecipeResponse recipeResponse = dtoMapper.toRecipeResponce(savedRecipe);
         return new ResponseEntity<>(recipeResponse,HttpStatus.CREATED);
    }
}
