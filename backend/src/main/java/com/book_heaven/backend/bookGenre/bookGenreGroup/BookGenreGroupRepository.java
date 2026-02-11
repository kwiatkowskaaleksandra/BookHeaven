package com.book_heaven.backend.bookGenre.bookGenreGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGenreGroupRepository extends JpaRepository<BookGenreGroup, Long> {

}
