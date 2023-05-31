package com.rsaad.recipe.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * POJO CategoryResponse Class  that consist of response object
 * which contain category response values displayed to client
 * @author Raed
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse extends RepresentationModel<CategoryResponse> {
    private Long id;
    private String category;
}
