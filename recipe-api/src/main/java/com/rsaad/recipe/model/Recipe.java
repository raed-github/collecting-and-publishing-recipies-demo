package com.rsaad.recipe.model;

import javax.persistence.Entity;

import com.rsaad.recipe.audit.Auditable;
import com.rsaad.recipe.constants.ApplicationConstants;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipe")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Recipe extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category",unique = true)
    @NotBlank(message = ApplicationConstants.RECIPE_NAME_IS_MANDATORY)
    private String name;

    @Column(name="yield")
    @PositiveOrZero(message = ApplicationConstants.YIELD_VALUE_MUST_BE_GREATER_OR_EQUAL_ZERO)
    private int yield;

    @Column(name="serves")
    @NotBlank(message=ApplicationConstants.SERVING_IS_MANDATORY)
    private String serves;

    @Column(name="cooking_time")
    private String cookingTime;

    @Column(name="preparation_time")
    @NotBlank(message=ApplicationConstants.PREPARATION_TIME_IS_MANDATORY)
    private String preparationTime;

    @Column(name="difficulty_level")
    @NotBlank(message = ApplicationConstants.DIFFICULTY_LEVEL_TIME_IS_MANDATORY)
    private String difficultyLevel;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_category",
            joinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "recipe_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Category> categories = new HashSet<Category>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_ingredient",
            joinColumns = {
                    @JoinColumn(name = "ingredient_id", referencedColumnName = "id",
                            nullable = false, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(name = "recipe_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<Ingredient> ingredients = new HashSet<Ingredient>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Direction> directions;
}

