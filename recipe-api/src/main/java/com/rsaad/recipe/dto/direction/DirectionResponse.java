package com.rsaad.recipe.dto.direction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO DirectionResponse Class  that consist of response object
 * which contain display Direction response values displayed to client
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectionResponse {
    private String step;
}
