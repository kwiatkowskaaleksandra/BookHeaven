package com.book_heaven.backend.bookGenre;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "bookGenre")
@Entity
@NoArgsConstructor
public class BookGenre {

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
}
