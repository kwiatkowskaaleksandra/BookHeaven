package com.book_heaven.backend.book;

import com.book_heaven.backend.book.dto.GoogleVolumesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
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

    public GoogleVolumesResponse search(String q, int maxResults, int startIndex) {
        if (q == null || q.isBlank()) {
            throw new IllegalArgumentException("Query 'q' cannot be blank");
        }
        if (maxResults < 1 || maxResults > 40) {
            throw new IllegalArgumentException("maxResults must be in [1..40]");
        }
        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex must be >= 0");
        }

        return client.get()
                .uri(uriBuilder -> {
                    var b = uriBuilder
                            .path("/v1/volumes")
                            .queryParam("q", q)
                            .queryParam("maxResults", maxResults)
                            .queryParam("startIndex", startIndex);

                    if (apiKey != null && !apiKey.isBlank()) {
                        b.queryParam("key", apiKey);
                    }
                    return b.build();
                })
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> { throw new IllegalStateException("Google Books 4xx: " + res.getStatusCode()); })
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> { throw new IllegalStateException("Google Books 5xx: " + res.getStatusCode()); })
                .body(GoogleVolumesResponse.class);
    }
}