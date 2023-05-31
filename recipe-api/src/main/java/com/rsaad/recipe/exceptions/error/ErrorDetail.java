package com.rsaad.recipe.exceptions.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ErrorDetail {
    private String message;
    private LocalDate date;
}
