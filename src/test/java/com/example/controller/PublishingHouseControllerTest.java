package com.example.controller;

import com.example.dto.PHouseDto;
import com.example.entity.PublishingHouse;
import com.example.mapper.PublishingHouseMapper;
import com.example.service.PublishingHouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublishingHouseControllerTest {

    @Mock
    PublishingHouseService publishingHouseService;
    @Spy
    PublishingHouseMapper publishingHouseMapper = Mappers.getMapper(PublishingHouseMapper.class);
    @InjectMocks
    PublishingHouseController publishingHouseController;

    @Test
    void addPublishingHouse() {
        when(publishingHouseService.addPublishingHouse(any(PublishingHouse.class))).thenReturn(new PublishingHouse());

        publishingHouseController.addPublishingHouse(new PHouseDto());

        InOrder inOrder = Mockito.inOrder(publishingHouseMapper, publishingHouseService);
        inOrder.verify(publishingHouseMapper).toPublishingHouse(any(PHouseDto.class));
        inOrder.verify(publishingHouseService).addPublishingHouse(any(PublishingHouse.class));
        inOrder.verify(publishingHouseMapper).toPHouseDto(any(PublishingHouse.class));
    }

    @Test
    void getPublishingHouseById() {
        when(publishingHouseService.getPublishingHouseById(any(Long.class))).thenReturn(new PublishingHouse());

        publishingHouseController.getPublishingHouseById(77L);

        InOrder inOrder = Mockito.inOrder(publishingHouseMapper, publishingHouseService);
        inOrder.verify(publishingHouseService).getPublishingHouseById(77L);
        inOrder.verify(publishingHouseMapper).toPHouseDto(any(PublishingHouse.class));
    }

    @Test
    void getAllPublishingHouses() {
        when(publishingHouseService.getAllPublishingHouses()).thenReturn(Collections.emptyList());

        publishingHouseController.getAllPublishingHouses();

        InOrder inOrder = Mockito.inOrder(publishingHouseMapper, publishingHouseService);
        inOrder.verify(publishingHouseService).getAllPublishingHouses();
        inOrder.verify(publishingHouseMapper).toPHouseDtoList(any());
    }

    @Test
    void updatePublishingHouse() {
        when(publishingHouseService.updatePublishingHouse(any(Long.class), any(PublishingHouse.class)))
                .thenReturn(new PublishingHouse());

        publishingHouseController.updatePublishingHouse(18L, new PHouseDto());

        InOrder inOrder = Mockito.inOrder(publishingHouseMapper, publishingHouseService);
        inOrder.verify(publishingHouseMapper).toPublishingHouse(any(PHouseDto.class));
        inOrder.verify(publishingHouseService).updatePublishingHouse(any(Long.class), any(PublishingHouse.class));
        inOrder.verify(publishingHouseMapper).toPHouseDto(any(PublishingHouse.class));
    }

    @Test
    void deletePublishingHouse() {
        doNothing().when(publishingHouseService).deletePublishingHouse(any(Long.class));

        publishingHouseController.deletePublishingHouse(13L);

        verify(publishingHouseService).deletePublishingHouse(13L);
    }

}