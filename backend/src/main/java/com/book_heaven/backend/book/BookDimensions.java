package com.book_heaven.backend.book;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Embeddable
@Getter
@Setter
@Accessors(chain = true)
public class BookDimensions {
    private Float widthMm;
    private Float heightMm;
    private Float thicknessMm;
    private Float weightG;
}
