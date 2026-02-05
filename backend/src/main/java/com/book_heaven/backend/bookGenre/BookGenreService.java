package com.book_heaven.backend.bookGenre;

import com.book_heaven.backend.bookGenre.dto.BookGenreDto;

public interface BookGenreService {
    BookGenre findByCode(String code);
    BookGenre addNewGenre(BookGenreDto bookGenreDto);
}
