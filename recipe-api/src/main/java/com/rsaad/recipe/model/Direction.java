package com.rsaad.recipe.model;

import com.rsaad.recipe.audit.Auditable;
import com.rsaad.recipe.constants.ApplicationConstants;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "direction")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direction extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "step")
    @NotBlank(message = ApplicationConstants.DIRECTIONS_IS_MANDATORY)
    @Lob
    private String step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
