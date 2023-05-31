package com.rsaad.recipe.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsaad.recipe.DataHolder;
import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.dto.recipe.RecipeCriteria;
import com.rsaad.recipe.dto.recipe.RecipeRequest;
import com.rsaad.recipe.dto.recipe.RecipeResponse;
import com.rsaad.recipe.exceptions.recipe.RecipeNameExistException;
import com.rsaad.recipe.exceptions.recipe.RecipeNotFoundException;
import com.rsaad.recipe.model.Recipe;
import com.rsaad.recipe.service.RecipeService;
import com.rsaad.recipe.service.impl.DtoMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DisplayName("Recipe Controller Integration Testing")
public class RecipeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DtoMapper dtoMapper;
    @MockBean
    private RecipeService recipeService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Recipe Controller findAll() Recipies")
    public void testFindAllShouldReturnSuccessAndStatusOk() throws Exception {
        RecipeCriteria recipeCriteria = RecipeCriteria.builder().build();
        Pageable pageable = null;

        when(recipeService.findAll(recipeCriteria, null)).thenReturn(DataHolder.returnRecipe());

        mockMvc.perform(get("/api/v1/recipies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Recipe Controller findById() Recipies")
    public void testFindByIdShouldReturnSuccessAndStatusOk() throws Exception {
        Long id = 1L;
        Recipe recipe = DataHolder.returnRecipe().get(0);
        RecipeResponse recipeResponse = new DtoMapper().toRecipeResponce(recipe);

        when(recipeService.findRecipeById(id)).thenReturn(recipe);

        when(dtoMapper.toRecipeResponce(recipe)).thenReturn(recipeResponse);

        mockMvc.perform(get("/api/v1/recipies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(ApplicationConstants.APPLICATION_HAL_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Recipe Controller findById() RecipeNotFoundException")
    public void testFindByIdRecipeNotFoundShouldReturnSuccessAndStatusNotFound() throws Exception {
        Long id = 1L;
        Recipe recipe = DataHolder.returnRecipe().get(0);
        RecipeResponse recipeResponse = new DtoMapper().toRecipeResponce(recipe);

        when(recipeService.findRecipeById(id))
                .thenThrow(new RecipeNotFoundException(ApplicationConstants.RECIPE_NOT_FOUND));

//        when(dtoMapper.toRecipeResponce(recipe)).thenReturn(recipeResponse);

        mockMvc.perform(get("/api/v1/recipies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Recipe Controller saveRecipe Recipe")
    public void testsaveRecipeShouldReturnSuccessAndStatusCreated() throws Exception {

//        when(recipeService.saveRecipe(DataHolder.returnRecipe().get(0))).thenReturn(DataHolder.returnRecipe().get(0));
//        when(recipeService.saveRecipe(DataHolder.returnRecipe().get(0)))
//                .thenThrow(new RecipeNameExistException(ApplicationConstants.CATEGORY_NOT_FOUND));
//
//        mockMvc.perform(post("/api/v1/recipies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(DataHolder.returnRecipe())))
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Recipe Controller saveRecipe with name that already exists")
    public void testsaveRecipeWithExistingNameShouldReturnSuccessAndStatusConflict() throws Exception {

        Recipe recipe = DataHolder.returnRecipe().get(0);
        RecipeRequest recipeRequest = new DtoMapper().toRecipeRequest(recipe);

        when(dtoMapper.toRecipe(recipeRequest)).thenReturn(recipe);

        when(recipeService.saveRecipe(recipe))
                .thenThrow(new RecipeNameExistException(ApplicationConstants.RECIPE_NAME_ALREADY_USED));

        mockMvc.perform(post("/api/v1/recipies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeRequest)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
