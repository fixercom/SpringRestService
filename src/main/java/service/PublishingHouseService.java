package service;

import entity.PublishingHouse;

import java.util.List;

public interface PublishingHouseService {

    PublishingHouse addPublishingHouse(PublishingHouse publishingHouse);

    PublishingHouse getPublishingHouseById(Long id);

    List<PublishingHouse> getAllPublishingHouses();

    PublishingHouse updatePublishingHouse(Long id, PublishingHouse publishingHouse);

    void deletePublishingHouse(Long id);

}
