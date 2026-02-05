package com.book_heaven.backend.book;

import com.book_heaven.backend.book.dto.BookDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/manage/addBook")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody BookDto bookDto) {
        bookService.addBook(bookDto);

        return ResponseEntity.ok().body("New book has been added.");
    }

    @GetMapping("/get/getBooksByGenre/{genre}")
    public ResponseEntity<?> getBookByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(bookService.getBooksByGenre(genre));
    }

    @GetMapping("/get/getBooksByAuthor/{authorId}")
    public ResponseEntity<?> getBookByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorId));
    }
}
