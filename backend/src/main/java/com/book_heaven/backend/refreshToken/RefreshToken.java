package com.book_heaven.backend.refreshToken;

import com.book_heaven.backend.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.Instant;

@Entity
@Table(name = "refreshToken")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @UniqueElements
    @Nullable
    private String token;

    @UniqueElements
    @Nullable
    private Instant expiredDate;
}
