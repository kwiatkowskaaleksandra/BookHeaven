package com.book_heaven.backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userrr")
public class UserController {

    @GetMapping("/signup")
    public ResponseEntity<?> signUp() {
        System.out.println("sedce");

        return ResponseEntity.ok().build();
    }
}
