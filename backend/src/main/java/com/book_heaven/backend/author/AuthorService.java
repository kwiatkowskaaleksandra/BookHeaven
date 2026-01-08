package com.book_heaven.backend.author;

public interface AuthorService {
    Author findByName(String name);
    Author addNewAuthor(String name);
}
