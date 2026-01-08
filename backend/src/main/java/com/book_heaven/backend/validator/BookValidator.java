package com.book_heaven.backend.validator;

import com.book_heaven.backend.book.dto.BookDto;
import com.book_heaven.backend.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Collection;

@Slf4j
@Service
public class BookValidator {

    public void bookChecking(BookDto book) {
        required(book, "Book");

        checkString(book.title(), "Title");
        checkString(book.publishingHouseName(), "Publishing House");
        checkString(book.language(), "Language");
        checkString(book.cover(), "Cover");
        checkString(book.description(), "Description");

        checkYear(book.publicationYear());
        checkPageCount(book.pageCount());
        checkDimension(book.height_mm());
        checkDimension(book.width_mm());
        checkDimension(book.thickness_mm());
        checkDimension(book.weight_g());
        checkPrice(book.price());
        checkIsbn(book.isbn13());

        checkNotEmpty(book.authorsName(), "Authors");
        checkNotEmpty(book.bookGenres(), "Book genres");

        book.bookGenres().forEach(bookGenre -> checkString(bookGenre, "Book genre"));
        book.authorsName().forEach(author -> checkString(author, "Author"));

    }

    private void checkString(String name, String type) {
        if(name == null || name.isBlank()) {
            log.debug("{} is blank or null.", type);
            throw new BookException("fieldsMustBeCompleted");
        }
    }

    private void checkNotEmpty(Collection<?> c, String type) {
        if (c == null || c.isEmpty()) {
            log.debug("{} is empty/null", type);
            throw new BookException("fieldsMustBeCompleted");
        }
    }

    private void checkYear(Integer value) {
        required(value, "Year");

        int current = Year.now().getValue();
        if (value < 1500 || value > current + 1) {
            log.debug("Year outside range: {}", value);
            throw new BookException("theSpecifiedYearIsOutsideTheRange");
        }
    }

    private void checkPageCount(Integer value) {
        required(value, "Page count");

        if (value < 10 || value > 2000) {
            log.debug("The specified page count is outside the range.");
            throw new BookException("theSpecifiedPageCountIsOutsideTheRange");
        }
    }

    private void checkPrice(BigDecimal price) {
        required(price, "Price");

        if (price.signum() < 0) {
            log.debug("Price must not be negative.");
            throw new BookException("priceMustBeNonNegative");
        }
    }

    private void checkDimension(Float dimension) {
        required(dimension, "Dimensions");

        if (dimension <= 0) {
            log.debug("Dimensions must not be outside the range.");
            throw new BookException("theSpecifiedDimensionAreOutsideTheRange");
        }
    }

    private void checkIsbn(String isbn) {
        if (!isValidIsbn13(isbn)) {
            log.debug("The ISBN entered is incorrect.");
            throw new BookException("theISBNEnteredIsIncorrect");
        }
    }

    private boolean isValidIsbn13(String isbn) {
        if (isbn == null || !isbn.matches("\\d{13}")) return false;
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int check = (10 - (sum % 10)) % 10;
        return check == (isbn.charAt(12) - '0');
    }

    private <T> void required(T value, String type) {
        if (value == null) {
            log.debug("Filed {} must be completed.", type);
            throw new BookException("fieldsMustBeCompleted");
        }
    }

}
