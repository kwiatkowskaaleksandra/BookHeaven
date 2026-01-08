package com.book_heaven.backend.publishingHouse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublishingHouseServiceImpl implements PublishingHouseService{

    private final PublishingHouseRepository publishingHouseRepository;

    @Override
    public PublishingHouse findByName(String name) {
        return publishingHouseRepository.findByName(name).orElseGet(() -> addNewPublishingHouse(name));
    }

    @Override
    public PublishingHouse addNewPublishingHouse(String name) {
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName(name);
        log.info("New publishing house has been added.");
        return publishingHouseRepository.save(publishingHouse);
    }
}
