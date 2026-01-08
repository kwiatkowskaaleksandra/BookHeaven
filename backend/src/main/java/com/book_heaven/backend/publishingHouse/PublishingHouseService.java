package com.book_heaven.backend.publishingHouse;

public interface PublishingHouseService {
    PublishingHouse findByName(String name);
    PublishingHouse addNewPublishingHouse(String name);
}
