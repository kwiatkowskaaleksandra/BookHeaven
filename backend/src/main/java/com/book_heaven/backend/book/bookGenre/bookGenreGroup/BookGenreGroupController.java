package com.book_heaven.backend.book.bookGenre.bookGenreGroup;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookGenreGroup")
public class BookGenreGroupController {
    private final BookGenreGroupService bookGenreGroupService;

    @GetMapping("/getAll")
    public ResponseEntity<List<BookGenreGroup>> getAllBookGenreGroups() {
        return ResponseEntity.ok(bookGenreGroupService.getBookGenreGroups());
    }
}
