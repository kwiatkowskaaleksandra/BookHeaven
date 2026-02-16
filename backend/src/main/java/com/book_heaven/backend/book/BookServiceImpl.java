package com.book_heaven.backend.book;

import com.book_heaven.backend.author.Author;
import com.book_heaven.backend.author.AuthorService;
import com.book_heaven.backend.book.dto.GoogleVolumesResponse;
import com.book_heaven.backend.publishingHouse.PublishingHouse;
import com.book_heaven.backend.publishingHouse.PublishingHouseService;
import com.book_heaven.backend.validator.IsbnValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublishingHouseService publishingHouseService;
    private final GoogleBooksClient googleBooksClient;


    @Override
    public void addBook(GoogleVolumesResponse bookDto) {

    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return List.of();
    }

    @Override
    public List<Book> getBooksByAuthor(Long name) {
        return List.of();
    }

    @Override
    @Transactional
    public Book importByIsbn(String rawIsbn) {
        String isbn = IsbnValidator.normalize(rawIsbn);
        if(!IsbnValidator.isValid(isbn)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Niepoprawny ISBN: " + rawIsbn);
        }


        if (isbn.length() == 13) {
            bookRepository.findByIsbn13(isbn).ifPresent(_ ->
            { throw new ResponseStatusException(HttpStatus.CONFLICT, "Książka o ISBN13 już istnieje: " + isbn); });
        } else {
            bookRepository.findByIsbn10(isbn).ifPresent(_ ->
            { throw new ResponseStatusException(HttpStatus.CONFLICT, "Książka o ISBN10 już istnieje: " + isbn); });
        }

        GoogleVolumesResponse resp = googleBooksClient.search("isbn:" + isbn, 1, 0);

        if (resp == null || resp.getItems() == null || resp.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono książki dla ISBN: " + isbn);
        }

        GoogleVolumesResponse.Item item = resp.getItems().getFirst();
        GoogleVolumesResponse.VolumeInfo vi = item.getVolumeInfo();

        String foundIsbn13  = extractIsbn13(vi);
        String foundIsbn10 = extractIsbn10(vi);

        Book book = new Book()
                .setTitle(vi.getTitle())
                .setLanguage(vi.getLanguage())
                .setDescription(vi.getDescription())
                .setPageCount(vi.getPageCount())
                .setCover(pickCover(vi))
                .setPublicationYear(parseYear(vi.getPublishedDate()))
                .setPrice(resolvePrice(item));

        if (isbn.length() == 13) book.setIsbn13(isbn);
        else book.setIsbn10(isbn);

        if (book.getIsbn13() == null && foundIsbn13 != null) book.setIsbn13(foundIsbn13);
        if (book.getIsbn10() == null && foundIsbn10 != null) book.setIsbn10(foundIsbn10);

        if (book.getIsbn10() == null && book.getIsbn13() == null) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY,
                    "Brak ISBN w danych Google Books");
        }

        if (book.getIsbn13() != null) {
            Book finalBook = book;
            bookRepository.findByIsbn13(book.getIsbn13()).ifPresent(_ ->
            { throw new ResponseStatusException(HttpStatus.CONFLICT, "Książka o ISBN13 już istnieje: " + finalBook.getIsbn13()); });
        }
        if (book.getIsbn10() != null) {
            Book finalBook1 = book;
            bookRepository.findByIsbn10(book.getIsbn10()).ifPresent(_ ->
            { throw new ResponseStatusException(HttpStatus.CONFLICT, "Książka o ISBN10 już istnieje: " + finalBook1.getIsbn10()); });
        }

        if(vi.getAuthors() != null) {
            for(String name: vi.getAuthors()) {
                Author a = authorService.findByNameIgnoreCase(name.trim());
                book.getAuthors().add(a);
            }
        }

        if(vi.getPublisher() != null && !vi.getPublisher().isBlank()) {
            String pubName = vi.getPublisher().trim();
            PublishingHouse ph = publishingHouseService.findByNameIgnoreCase(pubName);
            book.setPublishingHouse(ph);
        }

        book = bookRepository.save(book);

        log.info("New book was added.");
        return book;
    }

    private static String extractIsbn13(GoogleVolumesResponse.VolumeInfo vi) {
        if(vi.getIndustryIdentifiers() == null) return null;
        return vi.getIndustryIdentifiers().stream()
                .filter(x -> "ISBN_13".equalsIgnoreCase(x.getType()))
                .map(GoogleVolumesResponse.IndustryIdentifier::getIdentifier)
                .map(IsbnValidator::normalize)
                .filter(s -> s != null && s.matches("\\d{13}"))
                .findFirst().orElse(null);
    }

    private static String extractIsbn10(GoogleVolumesResponse.VolumeInfo vi) {
        if(vi.getIndustryIdentifiers() == null) return null;
        return vi.getIndustryIdentifiers().stream()
                .filter(x -> "ISBN_10".equalsIgnoreCase(x.getType()))
                .map(GoogleVolumesResponse.IndustryIdentifier::getIdentifier)
                .map(IsbnValidator::normalize)
                .filter(s -> s != null && s.matches("\\d{9}[\\dX]"))
                .findFirst().orElse(null);
    }

    private static Integer parseYear(String publishingDate) {
        if(publishingDate == null || publishingDate.length() < 4) return null;
        try {
            return Integer.parseInt(publishingDate.substring(0, 4));
        } catch (Exception e) {
            return null;
        }
    }

    private static String pickCover(GoogleVolumesResponse.VolumeInfo vi) {
        if(vi.getImageLinks() == null) return null;
        return (vi.getImageLinks().getThumbnail() != null) ? vi.getImageLinks().getThumbnail() : vi.getImageLinks().getSmallThumbnail();
    }

    private static BigDecimal resolvePrice(GoogleVolumesResponse.Item item) {
        var si = item.getSaleInfo();
        if(si != null && si.getRetailPrice() != null && si.getRetailPrice().getAmount() != null) {
            return si.getRetailPrice().getAmount();
        }
        return BigDecimal.ZERO;
    }
}
