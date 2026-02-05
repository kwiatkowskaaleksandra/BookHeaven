package com.book_heaven.backend.user;

import com.book_heaven.backend.exception.ActiveUserNotFoundException;
import com.book_heaven.backend.exception.DuplicatedUserInfoException;
import com.book_heaven.backend.exception.UserException;
import com.book_heaven.backend.user.dto.response.CurrentUser;
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
            throw new DuplicatedUserInfoException("EMAIL_ALREADY_EXISTS");
        }
        if (hasUserWithUsername(username)) {
            log.error("User with username: {} already exists.", username);
            throw new DuplicatedUserInfoException("USERNAME_ALREADY_EXISTS");
        }
//        System.out.println(email);
//        if (!email.matches("^[^@\\s]+@[^@\\s]+$")) {
//            log.error("The e-mail address provided is incorrectly formatted.");
//            throw new UserException("EMAIL_IS_INCORRECTLY_FORMATTED");
//        }
    }

    @Override
    public void saveNewUser(User user) {
        userRepository.save(user);
        log.info("The user {} has successfully registered.", user.getUsername());
    }

    @Override
    public CurrentUser getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User with username: " + username + " does not exists."));

        return new CurrentUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
    }
}
