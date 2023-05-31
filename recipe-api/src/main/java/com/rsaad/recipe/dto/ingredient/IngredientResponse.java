package com.rsaad.recipe.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO IngredientResponse Class  that consist of response object
 * which contain display ingredient response values displayed to client
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private String ingredient;
    private double quantity;
    private String unit;
}
