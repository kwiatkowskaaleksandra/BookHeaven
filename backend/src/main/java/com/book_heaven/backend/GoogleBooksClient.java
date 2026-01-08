package com.book_heaven.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GoogleBooksClient {

    private final RestClient client;
    private final String apiKey;

    public GoogleBooksClient(
            @Value("${google.books.base-url}") String baseUrl,
            @Value("${google.books.api-key}") String apiKey
    ) {
        this.client = RestClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public GoogleBooksResponse search(String q, int maxResults, int startIndex) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", q)
                        .queryParam("maxResults", maxResults)
                        .queryParam("startIndex", startIndex)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .body(GoogleBooksResponse.class);
    }
}