package com.example.service.impl;

import com.example.entity.PublishingHouse;
import com.example.repository.PublishingHouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublishingHouseServiceImplTest {

    @Mock
    PublishingHouseRepository publishingHouseRepository;
    @InjectMocks
    PublishingHouseServiceImpl publishingHouseService;

    @Test
    void addPublishingHouse() {
        when(publishingHouseRepository.save(any(PublishingHouse.class))).thenReturn(new PublishingHouse());

        publishingHouseService.addPublishingHouse(new PublishingHouse());

        verify(publishingHouseRepository).save(any(PublishingHouse.class));
    }

    @Test
    void getPublishingHouseById() {
        when(publishingHouseRepository.findById(any(Long.class))).thenReturn(Optional.of(new PublishingHouse()));

        publishingHouseService.getPublishingHouseById(17L);

        verify(publishingHouseRepository).findById(17L);
    }

    @Test
    void getAllPublishingHouses() {
        when(publishingHouseRepository.findAll()).thenReturn(Collections.emptyList());

        publishingHouseService.getAllPublishingHouses();

        verify(publishingHouseRepository).findAll();
    }

    @Test
    void updatePublishingHouse() {
        PublishingHouse publishingHouse = new PublishingHouse();
        when(publishingHouseRepository.save(any(PublishingHouse.class))).thenReturn(new PublishingHouse());

        publishingHouseService.updatePublishingHouse(15L, publishingHouse);

        assertEquals(15L, publishingHouse.getId());
        verify(publishingHouseRepository).save(any(PublishingHouse.class));
    }

    @Test
    void deletePublishingHouse() {
        doNothing().when(publishingHouseRepository).deleteById(anyLong());

        publishingHouseService.deletePublishingHouse(33L);

        verify(publishingHouseRepository).deleteById(33L);
    }
}