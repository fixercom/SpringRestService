package com.example.service.impl;

import com.example.entity.PublishingHouse;
import com.example.repository.PublishingHouseRepository;
import com.example.service.PublishingHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PublishingHouseServiceImpl implements PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;

    @Autowired
    public PublishingHouseServiceImpl(PublishingHouseRepository publishingHouseRepository) {
        this.publishingHouseRepository = publishingHouseRepository;
    }

    @Override
    @Transactional
    public PublishingHouse addPublishingHouse(PublishingHouse publishingHouse) {
        return publishingHouseRepository.save(publishingHouse);
    }

    @Override
    public PublishingHouse getPublishingHouseById(Long id) {
        return publishingHouseRepository.findById(id).orElseThrow();
    }

    @Override
    public List<PublishingHouse> getAllPublishingHouses() {
        return publishingHouseRepository.findAll();
    }

    @Override
    @Transactional
    public PublishingHouse updatePublishingHouse(Long id, PublishingHouse publishingHouse) {
        publishingHouse.setId(id);
        return publishingHouseRepository.save(publishingHouse);
    }

    @Override
    @Transactional
    public void deletePublishingHouse(Long id) {
        publishingHouseRepository.deleteById(id);
    }

}
