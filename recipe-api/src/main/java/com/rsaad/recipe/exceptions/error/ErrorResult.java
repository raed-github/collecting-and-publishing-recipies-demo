package com.rsaad.recipe.exceptions.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ErrorResult {
    private final List<ErrorDetail> fieldErrors = new ArrayList<>();
}
