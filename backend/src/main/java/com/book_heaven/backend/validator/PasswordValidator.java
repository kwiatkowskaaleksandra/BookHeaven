package com.book_heaven.backend.validator;

import com.book_heaven.backend.exception.PasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PasswordValidator {

    private static final int PASSWORD_MIN_LENGTH = 10;

    public void passwordChecking(String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            System.out.println(password+" "+repeatedPassword);
            log.error("The passwords provided are different.");
            throw new PasswordException("PASSWORD_PROVIDED_ARE_DIFFERENT");
        }
        validatePasswordStrength(password);
    }

    private void validatePasswordStrength(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            log.error("The password must be composed of at least 10 characters.");
            throw new PasswordException("PASSWORD_MIN_LENGTH");
        }
        if (!password.matches("(.*)[A-Z](.*)")) {
            log.error("The password must contain at least one uppercase letter");
            throw new PasswordException("PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_UPPERCASE_LETTER");
        }
        if (!password.matches("(.*)[a-z](.*)")) {
            log.error("The password must contain at least one lowercase letter.");
            throw new PasswordException("PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_LOWERCASE_LETTER");
        }
        if (!password.matches("(.*)[0-9](.*)")) {
            log.error("The password must contain at least one number.");
            throw new PasswordException("PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMBER");
        }
        if (!password.matches("(.*)[!@,().%&#$^-](.*)")) {
            log.error("The password must contain at least one special character.");
            throw new PasswordException("PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_SPECIAL_CHARACTER");
        }
    }
}
