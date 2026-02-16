package com.book_heaven.backend.author;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author findByNameIgnoreCase(String name) {
        return authorRepository.findByNameIgnoreCase(name).orElseGet(() -> addNewAuthor(name));
    }

    @Override
    public Author addNewAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        log.info("New author has been added.");
        return authorRepository.save(author);
    }
}
