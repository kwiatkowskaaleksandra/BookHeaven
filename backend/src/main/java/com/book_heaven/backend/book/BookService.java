package com.book_heaven.backend.book;

import com.book_heaven.backend.book.dto.GoogleVolumesResponse;

import java.util.List;

public interface BookService {
    void addBook(GoogleVolumesResponse bookDto);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByAuthor(Long name);
    Book importByIsbn(String rawIsbn);

}
