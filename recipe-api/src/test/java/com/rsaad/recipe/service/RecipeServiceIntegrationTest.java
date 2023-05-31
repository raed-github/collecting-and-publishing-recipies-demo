package com.rsaad.recipe.service;

import com.rsaad.recipe.DataHolder;
import com.rsaad.recipe.dto.recipe.RecipeCriteria;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.repository.RecipeRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DisplayName("Recipe Service Integration Testing")
public class RecipeServiceIntegrationTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @Order(1)
    @DisplayName("RecipeService save Recipe should return recipe")
    public void testSaveRecipeShouldReturnRecipe(){
        Category category = Category.builder().category("italian").build();
        Category savedCategory = categoryService.saveCategory(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("Italian food");
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        assertTrue(savedRecipe.getId()>0);
    }

    @Test
    @Order(2)
    @DisplayName("Recipe Service findAll Should return all recipies")
    public void testFindAllShouldReturnRecipeList(){
        List<Recipe> savedRecipes = recipeService.findAll(RecipeCriteria.builder().build(), Pageable.ofSize(10));
        assertTrue(savedRecipes.size()>=0);
        assertTrue(savedRecipes!=null);
    }

    @Test
    @Order(3)
    @DisplayName("Recipe Service find by id should return recipe")
    public void testFindByIdShouldReturnRecipe(){
        Category category =Category.builder().category("Low Carb").build();
        categoryService.saveCategory(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("Sald");
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        Recipe syncRecipe = recipeService.findRecipeById(savedRecipe.getId());
        assertTrue(syncRecipe!=null);
    }
}
