package com.rsaad.recipe.model;

import javax.persistence.Entity;

import com.rsaad.recipe.audit.Auditable;
import com.rsaad.recipe.constants.ApplicationConstants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    @NotBlank(message = ApplicationConstants.CATEGORY_NAME_IS_MANDATORY)
    private String category;

    @ManyToMany(mappedBy = "categories",cascade = CascadeType.ALL)
    private Set<Recipe> recipes = new HashSet<>();
}