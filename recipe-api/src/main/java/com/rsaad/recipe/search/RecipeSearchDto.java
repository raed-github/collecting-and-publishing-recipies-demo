package com.rsaad.recipe.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchDto {
    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;
}
