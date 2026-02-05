package com.book_heaven.backend.book;

import com.book_heaven.backend.bookGenre.BookGenre;
import com.book_heaven.backend.author.Author;
import com.book_heaven.backend.publishingHouse.PublishingHouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Table(name = "books")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    private String title;

    @ManyToOne
    @JoinColumn(name = "publishing_house_id")
    private PublishingHouse publishingHouse;

    private String language;

    private Integer publicationYear;

    private String cover;

    @Column(columnDefinition = "text") private String description;

    private Integer pageCount;

    @Column(precision = 12, scale = 2, nullable = false) private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<BookGenre> bookGenres = new HashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="widthMm", column=@Column(name="width_mm")),
            @AttributeOverride(name="heightMm", column=@Column(name="height_mm")),
            @AttributeOverride(name="thicknessMm", column=@Column(name="thickness_mm")),
            @AttributeOverride(name="weightG", column=@Column(name="weight_g"))
    })
    private BookDimensions dimensions;

    @Column(length = 13, unique = true)
    private String isbn13;

}
