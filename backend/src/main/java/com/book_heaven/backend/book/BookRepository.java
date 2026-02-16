package com.book_heaven.backend.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthors_id(Long id);
    List<Book> findByBookGenres_code(String name);
    Optional<Book> findByIsbn13(String isbn);
    Optional<Book> findByIsbn10(String isbn);
}
