package com.lavv.springboot.productapi.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
         UUID id,
         String name,
         String description,
         BigDecimal price,
         String imageUrl,
         Integer stockLevel,
         Instant createdAt,
         Instant updatedAt,
         Instant deletedAt
) {
}
