package com.book_heaven.backend.book.bookGenre;

import com.book_heaven.backend.book.bookGenre.bookGenreGroup.BookGenreGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookGenre")
@RequiredArgsConstructor
public class BookGenreController {
    private final BookGenreService bookGenreService;

    @GetMapping("/getAllBookGenre")
    public ResponseEntity<List<BookGenre>> getAllBookGenre() {
        return ResponseEntity.ok(bookGenreService.getBookGenre());
    }

    @GetMapping("/getAllBookGenreAndGroups")
    public ResponseEntity<Map<BookGenreGroup, List<BookGenre>>> getAllBookGenreAndGroups() {
        return ResponseEntity.ok(bookGenreService.getAllGenreAndGroups());
    }
}
