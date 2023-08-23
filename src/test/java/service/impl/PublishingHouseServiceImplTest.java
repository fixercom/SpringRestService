package service.impl;

import dao.PublishingHouseDao;
import entity.PublishingHouse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublishingHouseServiceImplTest {

    @Mock
    private PublishingHouseDao publishingHouseDao;
    @InjectMocks
    private PublishingHouseServiceImpl publishingHouseService;

    @Test
    void testAddPublishingHouse() {
        PublishingHouse publishingHouse = new PublishingHouse("Crown");
        when(publishingHouseDao.save(publishingHouse))
                .thenReturn(new PublishingHouse(1L, "Crown"));

        publishingHouseService.addPublishingHouse(publishingHouse);

        verify(publishingHouseDao).save(publishingHouse);
    }

    @Test
    void testGetPublishingHouseById() {
        when(publishingHouseDao.findById(1L))
                .thenReturn(Optional.of(new PublishingHouse(1L, "name")));

        publishingHouseService.getPublishingHouseById(1L);

        verify(publishingHouseDao).findById(1L);
    }

    @Test
    void testGetAllPublishingHouses() {
        when(publishingHouseDao.findAll()).thenReturn(Collections.emptyList());

        publishingHouseService.getAllPublishingHouses();

        verify(publishingHouseDao).findAll();
    }

    @Test
    void testUpdatePublishingHouse() {
        PublishingHouse publishingHouse = new PublishingHouse("Crown");
        when(publishingHouseDao.update(1L, publishingHouse))
                .thenReturn(new PublishingHouse(1L, "Crown"));

        publishingHouseService.updatePublishingHouse(1L, publishingHouse);

        verify(publishingHouseDao).update(1L, publishingHouse);
    }

    @Test
    void testDeletePublishingHouse() {
        doNothing().when(publishingHouseDao).delete(1L);

        publishingHouseService.deletePublishingHouse(1L);

        verify(publishingHouseDao).delete(1L);
    }

}