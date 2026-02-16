package com.book_heaven.backend.book.bookGenre.bookGenreGroup;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookGenreGroupServiceImpl implements BookGenreGroupService {

    private final BookGenreGroupRepository bookGenreGroupRepository;

    @Cacheable(cacheNames = "genre-groups", key = "'all'")
    @Override
    public List<BookGenreGroup> getBookGenreGroups() {
        return bookGenreGroupRepository.findAll();
    }
}
