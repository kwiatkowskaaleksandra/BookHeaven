package com.book_heaven.backend.publishingHouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {
    Optional<PublishingHouse> findByNameIgnoreCase(String name);
}
