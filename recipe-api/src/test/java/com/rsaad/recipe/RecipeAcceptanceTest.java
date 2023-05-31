package com.rsaad.recipe;

import com.rsaad.recipe.common.DataHolder;
import com.rsaad.recipe.dto.recipe.RecipeRequest;
import com.rsaad.recipe.dto.recipe.RecipeResponse;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.repository.CategoryRepository;
import com.rsaad.recipe.repository.RecipeRepository;
import com.rsaad.recipe.dto.DtoMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Acceptance Testing Class")
public class RecipeAcceptanceTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DtoMapper dtoMapper;

    private static final String RECIPE_API_URI = "/api/v1/recipies";

    private static final String CATEGORY_API_URI = "/api/v1/categories";

    @Test
    @Order(1)
    @DisplayName("Test save recipe should return ok")
    public void testSaveRecipeShouldReturnRecipeOkAndData() {
        Category category = Category.builder().category("vegan").build();
        categoryRepository.save(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("salad");
        RecipeRequest recipeRequest = dtoMapper.toRecipeRequest(recipe);

        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.postForEntity(RECIPE_API_URI, recipeRequest,
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(recipeResponseEntity.getBody().getName()).isNotEmpty();

    }

    @Test
    @Order(3)
    @DisplayName("Test save recipie with existing name should return conflict")
    public void testSaveRecipeWithExistingNameShouldReturnConflict() {
        Category category = Category.builder().category("vegit").build();
        categoryRepository.save(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("cezar salar");
        recipeRepository.save(recipe);

        RecipeRequest recipeRequest = dtoMapper.toRecipeRequest(recipe);

        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.postForEntity(RECIPE_API_URI, recipeRequest,
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(2)
    @DisplayName("Test find all recipies should return recipies list")
    public void testFindAllShouldReturnRecipeList(){
        Category category = Category.builder().category("europian").build();
        categoryRepository.save(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("burger42");
        recipeRepository.save(recipe);

        List<Recipe> recipeResponseEntity =
                restTemplate.getForObject("/api/v1/recipies",
                        List.class);

        assertThat(recipeResponseEntity.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @DisplayName("Test find recipe by id should return status ok")
    public void testFindByIdShouldReturnRecipe(){
        Category category = DataHolder.returnCategoryList().get(0);
        category.setCategory("xyz");
        categoryRepository.save(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("abcedfg");
        recipeRepository.save(recipe);
        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.getForEntity(RECIPE_API_URI+"/1",
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Order(6)
    @DisplayName("Test find all Search by recipe name")
    public void testFindAllSearchByRecipeNameShouldReturnRecipeOk(){
        Category category = Category.builder().category("vegi1").build();
        categoryRepository.save(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("burger1");
        recipeRepository.save(recipe);

        List recipeResponseEntity =
                restTemplate.getForObject("/api/v1/recipies?name={recipeName}",
                        List.class, Map.of("recipeName",recipe.getName()));

        assertThat(recipeResponseEntity.size()).isGreaterThan(0);

    }

    @Test
    @Order(7)
    @DisplayName("Test find all Search by recipe name and Category name")
    public void testFindAllSearchByRecipeNameAndCategoryNameShouldReturnRecipeOk(){
        Category category = Category.builder().category("vegi1").build();
        categoryRepository.save(category);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("burger12");
        recipe.setCategories(categories);
        recipeRepository.save(recipe);

        String url = "/api/v1/recipies?name="+recipe.getName()+"&category="+category.getCategory();
        List recipeResponseEntity =
                restTemplate.getForObject(url,
                        List.class);

        assertThat(recipeResponseEntity.size()).isGreaterThan(0);

    }

    @Test
    @Order(8)
    @DisplayName("Test save recipie with no name should return Bad Request")
    public void testSaveRecipeWithNoNameShouldReturnConflict() {
        Category category = Category.builder().category("vegit10").build();
        categoryRepository.save(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName(null);

        RecipeRequest recipeRequest = dtoMapper.toRecipeRequest(recipe);

        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.postForEntity(RECIPE_API_URI, recipeRequest,
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(9)
    @DisplayName("Test save recipie with no Category should return Bad Request")
    public void testSaveRecipeWithNoCategoryShouldReturnBadRequest() {
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("recipe11");
        recipe.setCategories(null);
        RecipeRequest recipeRequest = dtoMapper.toRecipeRequest(recipe);

        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.postForEntity(RECIPE_API_URI, recipeRequest,
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(10)
    @DisplayName("Test save recipie with no Direction should return Bad Request")
    public void testSaveRecipeWithNoDirectionShouldReturnConflict() {
        Category category = Category.builder().category("vegi111").build();
        categoryRepository.save(category);

        Set<Category> categories = new HashSet<>();
        categories.add(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("recipe13");
        recipe.setCategories(categories);
        recipe.setDirections(null);
        RecipeRequest recipeRequest = dtoMapper.toRecipeRequest(recipe);

        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.postForEntity(RECIPE_API_URI, recipeRequest,
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(11)
    @DisplayName("Test save recipie with no Ingredients should return Bad Request")
    public void testSaveRecipeWithNoIngredientsShouldReturnBadRequest() {
        Category category = Category.builder().category("vegi115").build();
        categoryRepository.save(category);

        Set<Category> categories = new HashSet<>();
        categories.add(category);
        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("recipe15");
        recipe.setCategories(categories);
        recipe.setIngredients(null);
        RecipeRequest recipeRequest = dtoMapper.toRecipeRequest(recipe);

        ResponseEntity<RecipeResponse> recipeResponseEntity =
                restTemplate.postForEntity(RECIPE_API_URI, recipeRequest,
                        RecipeResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(12)
    @DisplayName("Test find all Search by recipe name and Category name with sorting")
    public void testFindAllSearchByRecipeNameAndCategoryNameWithSortingShouldReturnRecipeOk(){
        Category category = Category.builder().category("vegi2").build();
        categoryRepository.save(category);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("burger5");
        recipe.setCategories(categories);

        recipeRepository.save(recipe);

        String url = "/api/v1/recipies?name="+recipe.getName()+"&category="+category.getCategory()+"&sort=name&direction=asc";
        List recipeResponseEntity =
                restTemplate.getForObject(url,
                        List.class);

        assertThat(recipeResponseEntity.size()).isGreaterThan(0);
    }

}
