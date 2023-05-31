package com.rsaad.recipe.dto.recipe;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.Column;

/**
 * POJO RecipeCriteria Class  that consist of request object
 * which will contain the recipe seached values
 * @author Raed
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecipeCriteria {
    private String name;
    private String category;
    private String services;
    private String cookingTime;
    private String preparationTime;
    private String difficultyLevel;
}
