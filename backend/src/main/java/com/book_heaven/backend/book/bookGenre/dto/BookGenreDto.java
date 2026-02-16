package com.book_heaven.backend.book.bookGenre.dto;

public record BookGenreDto(
        String code,
        String namePL,
        String nameEN,
        Boolean activity
) {
}
