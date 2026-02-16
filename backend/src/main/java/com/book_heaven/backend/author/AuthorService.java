package com.book_heaven.backend.author;

public interface AuthorService {
    Author findByNameIgnoreCase(String name);
    Author addNewAuthor(String name);
}
