package com.rsaad.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsaad.recipe.DataHolder;
import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.dto.category.CategoryRequest;
import com.rsaad.recipe.dto.category.CategoryResponse;
import com.rsaad.recipe.exceptions.recipe.RecipeNotFoundException;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.service.CategoryService;
import com.rsaad.recipe.service.impl.DtoMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DisplayName("Category Controller Integration Testing")
public class CategoryControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private DtoMapper dtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Category Controller findAllCategories() method")
    public void testFindAllShouldReturnSuccessAndStatusOk() throws Exception {

        when(categoryService.findAll()).thenReturn(DataHolder.returnCategoryList());

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Category Controller findById() method")
    public void testFindByIdShouldReturnSuccessAndStatusOk() throws Exception {
        Long id = 1L;
        Category category = DataHolder.returnCategoryList().get(0);
        CategoryResponse categoryResponse = new DtoMapper().toCategoryResponse(category);
        when(categoryService.findById(id)).thenReturn(category);

        when(dtoMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        mockMvc.perform(get("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(ApplicationConstants.APPLICATION_HAL_VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Category Controller findCategoryRecipies() method")
    public void testFindCategoryRecipiesShouldReturnSuccessAndStatusOk() throws Exception {
        Long id = 1L;
        when(categoryService.findCategoryRecipies(id)).thenReturn(DataHolder.returnRecipe());

        mockMvc.perform(get("/api/v1/categories/1/recipies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Category Controller saveCategory method")
    public void testSaveCategoryShouldReturnSuccessAndStatusCreated() throws Exception {
        Category category = DataHolder.returnCategoryList().get(0);
        CategoryRequest categoryRequest = new DtoMapper().toCategoryRequest(category);

        when(dtoMapper.toCategory(categoryRequest)).thenReturn(category);

        when(categoryService.saveCategory(category)).thenReturn(category);

        when(dtoMapper.toCategoryResponse(category)).thenReturn(new DtoMapper().toCategoryResponse(category));

        assertEquals(category, categoryService.saveCategory(category));

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(content().contentTypeCompatibleWith(ApplicationConstants.APPLICATION_HAL_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Category Controller findCategoryById() CategoryNotFoundException")
    public void testFindCategoryByIdShouldReturnExceptionStatusNotFound() throws Exception {
        when(categoryService.findCategoryRecipies(1L))
                .thenThrow(new RecipeNotFoundException(ApplicationConstants.RECIPE_NOT_FOUND));
        mockMvc.perform(get("/api/v1/categories/1/recipies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Category Controller findCategoryRecipies() CategoryNotFoundException")
    public void testFindCategoryRecipiesShouldReturnSuccessAndStatusNotFound() throws Exception {
        when(categoryService.findCategoryRecipies(1L))
                .thenThrow(new RecipeNotFoundException(ApplicationConstants.RECIPE_NOT_FOUND));

        mockMvc.perform(get("/api/v1/categories/1/recipies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
