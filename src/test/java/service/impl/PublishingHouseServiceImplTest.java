package service.impl;

import config.DataSource;
import dao.PublishingHouseDao;
import entity.PublishingHouse;
import exception.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublishingHouseServiceImplTest {

    @Mock
    private PublishingHouseDao publishingHouseDao;
    @Mock
    private Connection connection;
    @InjectMocks
    private PublishingHouseServiceImpl publishingHouseService;

    @Test
    void testAddPublishingHouse() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            PublishingHouse publishingHouse = new PublishingHouse("Crown");
            when(publishingHouseDao.save(publishingHouse, connection))
                    .thenReturn(new PublishingHouse(1L, "Crown"));

            publishingHouseService.addPublishingHouse(publishingHouse);

            datasource.verify(DataSource::getConnection);
            verify(publishingHouseDao).save(publishingHouse, connection);
        }
    }

    @Test
    void testAddPublishingHouseWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());
            PublishingHouse publishingHouse = new PublishingHouse("Crown");

            assertThrows(DaoException.class,
                    () -> publishingHouseService.addPublishingHouse(publishingHouse));
        }
    }

    @Test
    void testGetPublishingHouseById() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            when(publishingHouseDao.findById(1L, connection))
                    .thenReturn(Optional.of(new PublishingHouse(1L, "name")));

            publishingHouseService.getPublishingHouseById(1L);

            datasource.verify(DataSource::getConnection);
            verify(publishingHouseDao).findById(1L, connection);
        }
    }

    @Test
    void testGetPublishingHouseByIdWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> publishingHouseService.getPublishingHouseById(1L));
        }
    }

    @Test
    void testGetAllPublishingHouses() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            when(publishingHouseDao.findAll(connection)).thenReturn(Collections.emptyList());

            publishingHouseService.getAllPublishingHouses();

            datasource.verify(DataSource::getConnection);
            verify(publishingHouseDao).findAll(connection);
        }
    }

    @Test
    void testGetAllPublishingHousesWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> publishingHouseService.getAllPublishingHouses());
        }
    }

    @Test
    void testUpdatePublishingHouse() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            PublishingHouse publishingHouse = new PublishingHouse("Crown");
            when(publishingHouseDao.update(1L, publishingHouse, connection))
                    .thenReturn(new PublishingHouse(1L, "Crown"));

            publishingHouseService.updatePublishingHouse(1L, publishingHouse);

            datasource.verify(DataSource::getConnection);
            verify(publishingHouseDao).update(1L, publishingHouse, connection);
        }
    }

    @Test
    void testUpdatePublishingHouseWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());
            PublishingHouse publishingHouse = new PublishingHouse("Crown");

            assertThrows(DaoException.class,
                    () -> publishingHouseService.updatePublishingHouse(1L, publishingHouse));
        }
    }

    @Test
    void testDeletePublishingHouse() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenReturn(connection);
            doNothing().when(publishingHouseDao).delete(1L, connection);
            publishingHouseService.deletePublishingHouse(1L);

            datasource.verify(DataSource::getConnection);
            verify(publishingHouseDao).delete(1L, connection);
        }
    }

    @Test
    void testDeletePublishingHouseWhenSQLExceptionThenThrowDaoException() {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {
            datasource.when(DataSource::getConnection).thenThrow(new SQLException());

            assertThrows(DaoException.class, () -> publishingHouseService.deletePublishingHouse(1L));
        }
    }

}