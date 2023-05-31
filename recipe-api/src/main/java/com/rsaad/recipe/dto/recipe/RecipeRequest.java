package com.rsaad.recipe.dto.recipe;

import com.rsaad.recipe.dto.category.CategoryRequest;
import com.rsaad.recipe.dto.direction.DirectionRequest;
import com.rsaad.recipe.dto.ingredient.IngredientRequest;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Direction;
import com.rsaad.recipe.model.Ingredient;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * POJO RecipeRequest Class  that consist of request object
 * which will contain the client request
 * @author Raed
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecipeRequest {
    private Long id;
    private String name;
    private int yield;
    private String preparationTime;
    private String difficultyLevel;
    private String serves;
    private String cookingTime;
    private Set<DirectionRequest> directions;
    private Set<CategoryRequest> categories;
    private Set<IngredientRequest> ingredients;
}
