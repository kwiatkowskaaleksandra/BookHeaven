package com.book_heaven.backend.user;

import com.book_heaven.backend.user.dto.response.CurrentUser;

public interface UserService {
    void ensureActiveUserWithTheUsernameExists(String username);
    boolean hasUserWithEmail(String email);
    boolean hasUserWithUsername(String username);
    void checkUserData(String username, String email);
    void saveNewUser(User user);
    CurrentUser getUserByUsername(String username);
}
