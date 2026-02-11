package com.book_heaven.backend.bookGenre.bookGenreGroup;

import com.book_heaven.backend.bookGenre.BookGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Table(name = "bookGenreGroup")
@Entity
@Getter
@Setter
public class BookGenreGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String namePL;

    @Column(unique = true)
    private String nameEN;

    private Boolean active;

    @OneToMany(mappedBy = "bookGenreGroup", fetch = FetchType.EAGER)
    private Set<BookGenre> bookGenreSet;
}
