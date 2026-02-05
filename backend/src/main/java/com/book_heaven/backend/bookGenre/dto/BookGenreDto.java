package com.book_heaven.backend.bookGenre.dto;

public record BookGenreDto(
        String code,
        String namePL,
        String nameEN,
        Boolean activity
) {
}
