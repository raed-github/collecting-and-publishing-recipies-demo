package com.rsaad.recipe.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO IngredientRequest Class  that consist of request object
 * which will contain the client request
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequest {
    private String ingredient;
    private double quantity;
    private String unit;
}
