package com.book_heaven.backend.book.dto;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoogleVolumesResponse {
    private Integer totalItems;
    private List<Item> items;

    @Data
    public static class Item {
        private VolumeInfo volumeInfo;
        private SaleInfo saleInfo;
    }

    @Data
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String publisher;
        private String language;
        private String description;
        private Integer pageCount;
        private String publishedDate;
        private ImageLinks imageLinks;
        private List<IndustryIdentifier> industryIdentifiers;
        private List<String> categories;
    }

    @Data public static class ImageLinks {
        private String thumbnail;
        private String smallThumbnail;
    }

    @Data public static class IndustryIdentifier {
        private String type;
        private String identifier;
    }

    @Data public static class SaleInfo {
        private RetailPrice retailPrice;
    }

    @Data public static class RetailPrice {
        private BigDecimal amount;
        private String currencyCode;
    }
}
