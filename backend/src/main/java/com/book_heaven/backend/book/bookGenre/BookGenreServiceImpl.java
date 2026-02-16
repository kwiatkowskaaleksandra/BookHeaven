package com.book_heaven.backend.book.bookGenre;

import com.book_heaven.backend.book.bookGenre.bookGenreGroup.BookGenreGroup;
import com.book_heaven.backend.book.bookGenre.dto.BookGenreDto;
import com.book_heaven.backend.exception.BookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookGenreServiceImpl implements BookGenreService {

    private final BookGenreRepository bookGenreRepository;

    @Override
    public BookGenre findByCode(String code) {
        return bookGenreRepository.findByCode(code).orElseThrow(() -> {
            log.debug("Genre is missing.");
            return new BookException("genreIsMissing");
        });
    }

    @Override
    public BookGenre addNewGenre(BookGenreDto bookGenreDto) {
        return null;
    }

    @Override
    public List<BookGenre> getBookGenre() {
        return bookGenreRepository.findAll();
    }

    @Override
    public Map<BookGenreGroup, List<BookGenre>> getAllGenreAndGroups() {
        List<BookGenre> allBookGenre = getBookGenre();
        Map<BookGenreGroup, List<BookGenre>> map = new HashMap<>();
        allBookGenre.forEach(genre -> map.put(genre.getBookGenreGroup(), List.of(genre)));
        return map;
    }
}
