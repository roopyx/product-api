package com.lavv.springboot.productapi.exceptions;

import java.time.Instant;

public record ErrorResponse(
        String message,
        String error,
        int statusCode,
        String path,
        Instant timeStamp,
        java.util.Map<String, String> fieldErrors) {
}
