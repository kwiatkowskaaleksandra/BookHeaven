package com.book_heaven.backend.book;

import com.book_heaven.backend.BookGenre.BookGenre;
import com.book_heaven.backend.BookGenre.BookGenreService;
import com.book_heaven.backend.author.Author;
import com.book_heaven.backend.author.AuthorService;
import com.book_heaven.backend.book.dto.BookDto;
import com.book_heaven.backend.exception.BookException;
import com.book_heaven.backend.publishingHouse.PublishingHouse;
import com.book_heaven.backend.publishingHouse.PublishingHouseService;
import com.book_heaven.backend.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final AuthorService authorService;
    private final PublishingHouseService publishingHouseService;
    private final BookGenreService bookGenreService;

    @Override
    public void addBook(BookDto bookDto) {
        bookValidator.bookChecking(bookDto);

        Book book = mappingDtoToBook(bookDto);
        try {
            bookRepository.save(book);
            log.info("A new book has been added.");
        } catch (DataIntegrityViolationException e) {
            log.debug("Duplicate key.");
            throw new BookException("duplicateKey");
        }
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByBookGenres_code(genre.toUpperCase());
    }

    @Override
    public List<Book> getBooksByAuthor(Long id) {
        return bookRepository.findByAuthors_id(id);
    }

    private Book mappingDtoToBook(BookDto bookDto) {
        Set<Author> authorSet = new HashSet<>();
        bookDto.authorsName().forEach(a -> {
            Author author = authorService.findByName(a);
            authorSet.add(author);
        });

        PublishingHouse publishingHouse = publishingHouseService.findByName(bookDto.publishingHouseName());
        BookDimensions bookDimensions = new BookDimensions()
                .setWidthMm(bookDto.width_mm())
                .setHeightMm(bookDto.height_mm())
                .setThicknessMm(bookDto.thickness_mm())
                .setWeightG(bookDto.weight_g());

        Set<BookGenre> bookGenres = new HashSet<>();
        bookDto.bookGenres().forEach(genre -> {
            BookGenre bookGenre = bookGenreService.findByCode(genre);
            bookGenres.add(bookGenre);
        });

        return new Book()
                .setTitle(bookDto.title())
                .setAuthors(authorSet)
                .setPublishingHouse(publishingHouse)
                .setLanguage(bookDto.language())
                .setPublicationYear(bookDto.publicationYear())
                .setCover(bookDto.cover())
                .setDescription(bookDto.description())
                .setPageCount(bookDto.pageCount())
                .setPrice(bookDto.price())
                .setBookGenres(bookGenres)
                .setDimensions(bookDimensions)
                .setIsbn13(bookDto.isbn13());
    }
}
