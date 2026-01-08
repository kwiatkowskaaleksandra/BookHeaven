package com.book_heaven.backend.BookGenre.dto;

public record BookGenreDto(
        String code,
        String namePL,
        String nameEN,
        Boolean activity
) {
}
