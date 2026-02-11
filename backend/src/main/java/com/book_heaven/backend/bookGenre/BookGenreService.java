package com.book_heaven.backend.bookGenre;

import com.book_heaven.backend.bookGenre.bookGenreGroup.BookGenreGroup;
import com.book_heaven.backend.bookGenre.dto.BookGenreDto;

import java.util.List;
import java.util.Map;

public interface BookGenreService {
    BookGenre findByCode(String code);
    BookGenre addNewGenre(BookGenreDto bookGenreDto);
    List<BookGenre> getBookGenre();
    Map<BookGenreGroup, List<BookGenre>> getAllGenreAndGroups();
}
