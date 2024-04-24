package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.exception;

import com.salesmanagement.salesmagament.domain.exception.PriceBadRequestException;
import com.salesmanagement.salesmagament.domain.exception.PriceNotFoundException;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.exception.ErrorCatalog.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PriceNotFoundException.class)
    public ErrorResponse handleStudentNotFoundException() {
        return ErrorResponse.builder()
                .code(PRICE_NOT_FOUND.getCode())
                .message(PRICE_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PriceBadRequestException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            PriceBadRequestException exception) {


        return ErrorResponse.builder()
                .code(INVALID_PRICE.getCode())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericError(Exception exception) {
        return ErrorResponse.builder()
                .code(GENERIC_ERROR.getCode())
                .message(GENERIC_ERROR.getMessage())
                .details(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }

}
