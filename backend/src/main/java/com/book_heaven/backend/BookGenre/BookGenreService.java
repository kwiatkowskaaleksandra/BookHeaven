package com.book_heaven.backend.BookGenre;

import com.book_heaven.backend.BookGenre.dto.BookGenreDto;

public interface BookGenreService {
    BookGenre findByCode(String code);
    BookGenre addNewGenre(BookGenreDto bookGenreDto);
}
