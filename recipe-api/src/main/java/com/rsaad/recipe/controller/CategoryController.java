package com.rsaad.recipe.controller;

import com.rsaad.recipe.dto.category.CategoryRequest;
import com.rsaad.recipe.dto.category.CategoryResponse;
import com.rsaad.recipe.dto.recipe.RecipeResponse;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.service.CategoryService;
import com.rsaad.recipe.service.impl.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Category Controller class which will be exposed.
 * @author Raed
 *
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DtoMapper dtoMapper;

//    @LogExecutionTime
    @GetMapping
    public ResponseEntity findAllCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryResponse> categoryResponses = categories.stream().map(dtoMapper::toCategoryResponse).toList();
        return new ResponseEntity(categoryResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity(dtoMapper.toCategoryResponse(category).removeLinks(),HttpStatus.OK);
    }

    @GetMapping("/{id}/recipies")
    public ResponseEntity findCategoryRecipies(@PathVariable Long id){
        List<Recipe> recipies = categoryService.findCategoryRecipies(id);
        List<RecipeResponse> responses = recipies.stream().map(dtoMapper::toRecipeResponce).toList();
        return new ResponseEntity(responses,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        Category savedCategory = categoryService.saveCategory(dtoMapper.toCategory(categoryRequest));
        return new ResponseEntity<>(dtoMapper.toCategoryResponse(savedCategory),HttpStatus.CREATED);
    }
}
