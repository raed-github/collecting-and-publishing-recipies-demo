package com.rsaad.recipe.dto.recipe;

import com.rsaad.recipe.dto.category.CategoryResponse;
import com.rsaad.recipe.dto.direction.DirectionResponse;
import com.rsaad.recipe.dto.ingredient.IngredientResponse;
import com.rsaad.recipe.model.Category;
import com.rsaad.recipe.model.Direction;
import com.rsaad.recipe.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

/**
 * POJO RecipeResponse Class  that consist of response object
 * which contain recipe response values displayed to client
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse extends RepresentationModel<RecipeResponse> {
    private String name;
    private int yield;
    private String preparationTime;
    private String difficultyLevel;
    private String serves;
    private String cookingTime;
    private Set<DirectionResponse> directions;
    private Set<CategoryResponse> categories;
    private Set<IngredientResponse> ingredients;
}
