package com.rsaad.recipe.exceptions.controller;

import com.rsaad.recipe.constants.ApplicationConstants;
import com.rsaad.recipe.exceptions.MissingFromRequestException;
import com.rsaad.recipe.exceptions.error.ErrorResult;
import com.rsaad.recipe.exceptions.recipe.RecipeNameExistException;
import com.rsaad.recipe.exceptions.recipe.RecipeNotFoundException;
import com.rsaad.recipe.exceptions.error.ErrorDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDate;

/**
 * Exception Controller class which will handle all exceptions including thrown and error exceptions.
 * @author Raed
 *
 */
@ControllerAdvice
public class RecipeExceptionController {

    @ExceptionHandler(value = {RecipeNameExistException.class})
    public ResponseEntity<Object> exception(RecipeNameExistException exception){
        return new ResponseEntity<Object>(ErrorDetail.builder().message(exception.getMessage())
                .date(LocalDate.now())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = RecipeNotFoundException.class)
    public ResponseEntity<Object> exception(RecipeNotFoundException exception){
        return new ResponseEntity<Object>(ErrorDetail.builder().message(exception.getMessage())
                .date(LocalDate.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MissingFromRequestException.class)
    public ResponseEntity<Object> exception(MissingFromRequestException exception){
        return new ResponseEntity<Object>(ErrorDetail.builder().message(exception.getMessage())
                .date(LocalDate.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResult errorResult = new ErrorResult();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorResult.getFieldErrors().add(
                    ErrorDetail.builder()
                            .message(violation.getMessage())
                            .date(LocalDate.now())
                            .build());
        }
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> exception(Exception exception){
        if(exception instanceof DataIntegrityViolationException){
            return new ResponseEntity<Object>(ErrorDetail.builder().message(ApplicationConstants.PLEASE_MAKE_SURE_ALL_REQUIRED_IDS_ARE_PASSED)
                    .date(LocalDate.now())
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(ErrorDetail.builder().message(ApplicationConstants.INTERNAL_SERVER_ERROR_CONTACT_ADMIN)
                .date(LocalDate.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}