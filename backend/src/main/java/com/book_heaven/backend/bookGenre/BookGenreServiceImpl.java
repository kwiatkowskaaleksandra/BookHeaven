package com.book_heaven.backend.bookGenre;

import com.book_heaven.backend.bookGenre.dto.BookGenreDto;
import com.book_heaven.backend.exception.BookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
