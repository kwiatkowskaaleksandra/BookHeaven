package com.book_heaven.backend.book.bookGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    Optional<BookGenre> findByCode(String code);

}
