package com.book_heaven.backend.publishingHouse;

public interface PublishingHouseService {
    PublishingHouse findByNameIgnoreCase(String name);
    PublishingHouse addNewPublishingHouse(String name);
}
