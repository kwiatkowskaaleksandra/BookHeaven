package com.book_heaven.backend.user;

public interface UserService {
    void ensureActiveUserWithTheUsernameExists(String username);
    boolean hasUserWithEmail(String email);
    boolean hasUserWithUsername(String username);
    void checkUserData(String username, String email);
    void saveNewUser(User user);
}
