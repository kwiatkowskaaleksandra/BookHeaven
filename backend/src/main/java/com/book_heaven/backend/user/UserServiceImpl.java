package com.book_heaven.backend.user;

import com.book_heaven.backend.exception.ActiveUserNotFoundException;
import com.book_heaven.backend.exception.DuplicatedUserInfoException;
import com.book_heaven.backend.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void ensureActiveUserWithTheUsernameExists(String username) {
        userRepository.findByUsernameAndActivity(username, true)
                .orElseThrow(() -> new ActiveUserNotFoundException(String.format("There is no username %s.", username)));
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void checkUserData(String username, String email) {
        if(hasUserWithEmail(email)) {
            log.error("User with e-mail address: {} already exists.", email);
            throw new DuplicatedUserInfoException("emailAlreadyExists");
        }
        if (hasUserWithUsername(username)) {
            log.error("User with username: {} already exists.", username);
            throw new DuplicatedUserInfoException("usernameAlreadyExists");
        }
        if (!email.matches("(.*)@(.*)")) {
            log.error("The e-mail address provided is incorrectly formatted.");
            throw new UserException("emailIsIncorrectlyFormatted");
        }
    }

    @Override
    public void saveNewUser(User user) {
        userRepository.save(user);
        log.info("The user {} has successfully registered.", user.getUsername());
    }
}
