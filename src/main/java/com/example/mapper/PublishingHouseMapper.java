package com.example.mapper;

import com.example.dto.PHouseDto;
import com.example.entity.PublishingHouse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PublishingHouseMapper {

    PHouseDto toPHouseDto(PublishingHouse publishingHouse);

    PublishingHouse toPublishingHouse(PHouseDto pHouseDto);

    List<PHouseDto> toPHouseDtoList(List<PublishingHouse> publishingHouses);

}
