package com.example.controller;

import com.example.entity.PublishingHouse;
import com.example.service.PublishingHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishing_houses")
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    @Autowired
    public PublishingHouseController(PublishingHouseService publishingHouseService) {
        this.publishingHouseService = publishingHouseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublishingHouse addPublishingHouse(@RequestBody PublishingHouse publishingHouse) {
        return publishingHouseService.addPublishingHouse(publishingHouse);
    }

    @GetMapping("/{publishingHouseId}")
    public PublishingHouse getPublishingHouseById(@PathVariable Long publishingHouseId) {
        return publishingHouseService.getPublishingHouseById(publishingHouseId);
    }

    @GetMapping
    public List<PublishingHouse> getAllPublishingHouses() {
        return publishingHouseService.getAllPublishingHouses();
    }

    @PutMapping("/{publishingHouseId}")
    public PublishingHouse updatePublishingHouse(@PathVariable Long publishingHouseId,
                                        @RequestBody PublishingHouse publishingHouse) {
        return publishingHouseService.updatePublishingHouse(publishingHouseId, publishingHouse);
    }

    @DeleteMapping("/{publishingHouseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublishingHouse(@PathVariable Long publishingHouseId) {
        publishingHouseService.deletePublishingHouse(publishingHouseId);
    }

}
