package com.rsaad.recipe.model;

import com.rsaad.recipe.audit.Auditable;
import com.rsaad.recipe.constants.ApplicationConstants;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredient")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ingredient")
    @NotBlank(message=ApplicationConstants.INGREDIENT_IS_MANDATORY)
    private String ingredient;

    @Column(name = "quantity")
    @Positive(message = ApplicationConstants.QUANTITY_VALUE_MUST_BE_GREATER_THAN_ZERO)
    private double quantity;

    @Column(name = "unit")
    private String unit;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private Set<Recipe> recipes = new HashSet<>();

}
