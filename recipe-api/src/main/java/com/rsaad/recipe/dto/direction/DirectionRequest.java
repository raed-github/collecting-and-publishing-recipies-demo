package com.rsaad.recipe.dto.direction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO DirectionRequest Class  that consist of request object
 * which will contain the client request
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectionRequest {
    private String step;
}
