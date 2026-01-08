package com.book_heaven.backend.book.dto;

import java.math.BigDecimal;
import java.util.Set;

public record BookDto(
        String isbn13,
        String title,
        Set<String> authorsName,
        String publishingHouseName,
        String language,
        Integer publicationYear,
        String cover,
        String description,
        Integer pageCount,
        BigDecimal price,
        Set<String> bookGenres,
        Float width_mm,
        Float height_mm,
        Float thickness_mm,
        Float weight_g
) {
}
