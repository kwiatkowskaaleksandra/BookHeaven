package com.book_heaven.backend.validator;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class IsbnValidator {

    public static String normalize(String raw) {
        if (raw == null) return null;

        return raw.replaceAll("[^0-9Xx]", "").toUpperCase();
    }

    public static boolean isValid(String raw) {
        String isbn = normalize(raw);
        if(isbn == null) return false;
        if(isbn.length() == 13) return isValidIsbn13(isbn);
        if(isbn.length() == 10) return isValidIsbn10(isbn);

        return false;
    }

    private static boolean isValidIsbn13(String isbn13) {
        if(!isbn13.matches("\\d{13}")) return false;
        int sum = 0;
        for(int i = 0; i < 12; i++) {
            int d = isbn13.charAt(i) - '0';
            sum += (i % 2 == 0) ? d : d * 3;
        }
        int check = (10 - (sum % 10)) % 10;

        return check == (isbn13.charAt(12) - '0');
    }

    private static boolean isValidIsbn10(String isbn10) {
        if(!isbn10.matches("\\d{9}[\\dX]")) return false;
        int sum = 0;
        for(int i = 0; i < 9; i++) {
            sum += (isbn10.charAt(i) - '0') * (10 - i);
        }
        char last = isbn10.charAt(9);
        int check = (last == 'X') ? 10 : (last - '0');
        sum += check;

        return sum % 11 == 0;
    }
}
