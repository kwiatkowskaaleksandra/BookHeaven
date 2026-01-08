package com.book_heaven.backend.book;

import com.book_heaven.backend.book.dto.BookDto;

import java.util.List;

public interface BookService {
    void addBook(BookDto bookDto);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByAuthor(Long name);
}
