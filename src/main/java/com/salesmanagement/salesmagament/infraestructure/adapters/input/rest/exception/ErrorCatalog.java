package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.exception;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    PRICE_NOT_FOUND("ERR_PRICE_001", "Price not found."),
    INVALID_PRICE("ERR_PRICE_002", "Invalid Price parameters."),
    GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred.");

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
