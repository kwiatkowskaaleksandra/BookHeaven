package com.book_heaven.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndActivity(String username, boolean activity);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
