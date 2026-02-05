package com.book_heaven.backend.user.dto.response;

import com.book_heaven.backend.role.Role;

import java.util.Set;

public record CurrentUser (Long id, String username, String email, Set<Role> roles) {
}
