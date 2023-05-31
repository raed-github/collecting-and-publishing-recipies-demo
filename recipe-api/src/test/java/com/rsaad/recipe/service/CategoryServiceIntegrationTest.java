package com.rsaad.recipe.service;

import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.repository.CategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DisplayName("Category Service Integration Testing")
public class CategoryServiceIntegrationTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    @Order(1)
    @DisplayName("CategoryService save category should return category")
    public void testSaveCategoryShouldReturnCategory(){
        Category category = Category.builder().category("ketogenic1").build();
        Category savedCategory = categoryService.saveCategory(category);
        assertTrue(savedCategory!=null);
    }

    @Test
    @Order(2)
    @DisplayName("Category Service findAll should return category list")
    public void testFindAllShouldReturnCategoryList(){
        List<Category> savedCategory = categoryService.findAll();
        assertTrue(savedCategory.size()>0);
    }

    @Test
    @DisplayName("Category Service find by id should return Category")
    public void testFindByIdShouldReturnCategory(){
        Category category = categoryService.saveCategory(Category.builder().category("vegan").build());
        Category savedCategory = categoryService.findById(category.getId());
        assertTrue(category!=null);
    }
}
