package com.rsaad.recipe.dto.category;

import lombok.*;

/**
 * POJO CategoryRequest Class  that consist of request object
 * which will contain the client request
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private Long id;
    private String category;
}
