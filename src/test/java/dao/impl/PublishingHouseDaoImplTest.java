package dao.impl;

import config.TestDataSource;
import dao.AuthorDao;
import dao.PublishingHouseDao;
import entity.Author;
import entity.PublishingHouse;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PublishingHouseDaoImplTest {

    private static final PublishingHouseDao publishingHouseDao = PublishingHouseDaoImpl.getInstance();

    @Test
    void testSave() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = new PublishingHouse("name");
            PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse, connection);

            assertAll(
                    () -> assertNotNull(savedPublishingHouse.getId(),
                            "Saved publishingHouse id must not be null"),
                    () -> assertEquals(1, publishingHouseDao.findAll(connection).size(),
                            "The findAll method must return a list with size = 1")
            );

            publishingHouseDao.delete(savedPublishingHouse.getId(), connection);
        }
    }

    @Test
    void testFindById() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = new PublishingHouse("name");
            PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse, connection);

            assertAll(
                    () -> assertNotEquals(Optional.empty(),
                            publishingHouseDao.findById(savedPublishingHouse.getId(), connection),
                            "The findById method for savedPublishingHouse must return a non-empty Optional"),
                    () -> assertEquals(Optional.empty(),
                            publishingHouseDao.findById(Long.MAX_VALUE, connection),
                            "The findById method for Long.MAX_VALUE must return empty Optional")
            );

            publishingHouseDao.delete(savedPublishingHouse.getId(), connection);
        }
    }

    @Test
    void testFindAll() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = new PublishingHouse("name");
            PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse, connection);

            assertAll(
                    () -> assertEquals(1, publishingHouseDao.findAll(connection).size(),
                            "The findAll method must return a list with size = 1")
            );

            publishingHouseDao.delete(savedPublishingHouse.getId(), connection);
        }
    }

    @Test
    void testUpdate() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = new PublishingHouse("name");
            PublishingHouse publishingHouseForUpdate = new PublishingHouse("new");
            PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse, connection);
            publishingHouseDao.update(savedPublishingHouse.getId(), publishingHouseForUpdate, connection);

            assertAll(
                    () -> assertEquals(1, publishingHouseDao.findAll(connection).size(),
                            "The findAll method must return a list with size = 1"),
                    () -> assertEquals("new",
                            publishingHouseDao.findById(savedPublishingHouse.getId(), connection).get().getName(),
                            "New name for saved PublishingHouse must be equal 'new'")
            );

            publishingHouseDao.delete(savedPublishingHouse.getId(), connection);
        }
    }

    @Test
    void testDelete() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()) {
            PublishingHouse publishingHouse = new PublishingHouse("name");
            PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse, connection);

            assertEquals(1, publishingHouseDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 1");

            publishingHouseDao.delete(savedPublishingHouse.getId(), connection);

            assertEquals(0, publishingHouseDao.findAll(connection).size(),
                    "The findAll method must return a list with size = 0");
        }
    }
}