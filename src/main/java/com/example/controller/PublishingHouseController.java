package com.example.controller;

import com.example.dto.PHouseDto;
import com.example.entity.PublishingHouse;
import com.example.mapper.PublishingHouseMapper;
import com.example.service.PublishingHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishing_houses")
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;
    private final PublishingHouseMapper publishingHouseMapper;

    @Autowired
    public PublishingHouseController(PublishingHouseService publishingHouseService,
                                     PublishingHouseMapper publishingHouseMapper) {
        this.publishingHouseService = publishingHouseService;
        this.publishingHouseMapper = publishingHouseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PHouseDto addPublishingHouse(@RequestBody PHouseDto pHouseDto) {
        PublishingHouse publishingHouse = publishingHouseMapper.toPublishingHouse(pHouseDto);
        return publishingHouseMapper.toPHouseDto(publishingHouseService.addPublishingHouse(publishingHouse));
    }

    @GetMapping("/{publishingHouseId}")
    public PHouseDto getPublishingHouseById(@PathVariable Long publishingHouseId) {
        return publishingHouseMapper.toPHouseDto(publishingHouseService.getPublishingHouseById(publishingHouseId));
    }

    @GetMapping
    public List<PHouseDto> getAllPublishingHouses() {
        return publishingHouseMapper.toPHouseDtoList(publishingHouseService.getAllPublishingHouses());
    }

    @PutMapping("/{publishingHouseId}")
    public PHouseDto updatePublishingHouse(@PathVariable Long publishingHouseId,
                                                 @RequestBody PHouseDto pHouseDto) {
        PublishingHouse publishingHouse = publishingHouseMapper.toPublishingHouse(pHouseDto);
        return publishingHouseMapper.toPHouseDto(publishingHouseService
                .updatePublishingHouse(publishingHouseId, publishingHouse));
    }

    @DeleteMapping("/{publishingHouseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublishingHouse(@PathVariable Long publishingHouseId) {
        publishingHouseService.deletePublishingHouse(publishingHouseId);
    }

}
