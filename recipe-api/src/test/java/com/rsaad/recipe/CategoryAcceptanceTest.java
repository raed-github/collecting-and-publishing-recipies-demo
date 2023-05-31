package com.rsaad.recipe;

import com.rsaad.recipe.common.DataHolder;
import com.rsaad.recipe.dto.category.CategoryRequest;
import com.rsaad.recipe.dto.category.CategoryResponse;
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
public class CategoryAcceptanceTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private DtoMapper dtoMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String API_URI = "/api/v1/categories";

    private static final String RECIPE_API_URI = "/api/v1/recipies";

    private static final String CATEGORY_API_URI = "/api/v1/categories";

    @Test
    @Order(1)
    @DisplayName("Test Create Category")
    public void testSaveCategoryShouldReturnStatusCreated() {
        Category category = Category.builder().category("vegan").build();

        CategoryRequest categoryRequest = dtoMapper.toCategoryRequest(category);

        ResponseEntity<CategoryResponse> categoryResponseEntity =
                restTemplate.postForEntity(CATEGORY_API_URI, categoryRequest,
                        CategoryResponse.class);

        assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    @DisplayName("Test Create Category with existing name")
    public void testSaveCategoryWithExistingNameShouldReturnConflict() {
        Category category = Category.builder().category("vegi").build();
        categoryRepository.save(category);

        CategoryRequest categoryRequest = dtoMapper.toCategoryRequest(category);

        ResponseEntity<CategoryResponse> categoryResponseEntity =
                restTemplate.postForEntity(CATEGORY_API_URI, categoryRequest,
                        CategoryResponse.class);

        assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(3)
    @DisplayName("Test saving category with no name")
    public void testSaveCategoryWithNoNameShouldReturnConflict() {
        Category category = Category.builder().build();

        CategoryRequest categoryRequest = dtoMapper.toCategoryRequest(category);

        ResponseEntity<CategoryResponse> categoryResponseEntity =
                restTemplate.postForEntity(CATEGORY_API_URI, categoryRequest,
                        CategoryResponse.class);

        assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(3)
    @DisplayName("Test find all categories")
    public void testFindAllShouldReturnRecipeOk(){
        Category category = Category.builder().category("vegi").build();
        categoryRepository.save(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("burger");
        recipeRepository.save(recipe);
        List recipeResponseEntity =
                restTemplate.getForObject(RECIPE_API_URI,
                        List.class);

        assertThat(recipeResponseEntity.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @DisplayName("Test find category by id should return status ok")
    public void testFindByIdShouldReturnStatusOk(){
        Category category = Category.builder().category("fast food").build();
        categoryRepository.save(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipeRepository.save(recipe);
        ResponseEntity<CategoryResponse> recipeResponseEntity =
                restTemplate.getForEntity(CATEGORY_API_URI+"/"+category.getId(),
                        CategoryResponse.class);

        assertThat(recipeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    @DisplayName("Test find category recipies OK")
    public void testFindCategoryShouldReturnRecipeOk(){
        Category category = Category.builder().category("burger").build();
        categoryRepository.save(category);
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(category);

        Recipe recipe = DataHolder.returnRecipe().get(0);
        recipe.setName("burgers");
        recipe.setCategories(categorySet);
        recipeRepository.save(recipe);

        List recipeResponseEntity =
                restTemplate.getForObject("/api/v1/categories/{id}/recipies",
                        List.class,Map.of("id", "1"));

        assertThat(recipeResponseEntity.size()).isGreaterThan(0);
    }

}
