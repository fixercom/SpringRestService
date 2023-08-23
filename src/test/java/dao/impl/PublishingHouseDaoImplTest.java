package dao.impl;

import config.TestPostgresContainer;
import dao.PublishingHouseDao;
import entity.PublishingHouse;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PublishingHouseDaoImplTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestPostgresContainer.getInstance();

    private static final PublishingHouseDao publishingHouseDao = PublishingHouseDaoImpl.getInstance();

    @Test
    void testSave() throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse("name");
        PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse);

        assertAll(
                () -> assertNotNull(savedPublishingHouse.getId(),
                        "Saved publishingHouse id must not be null"),
                () -> assertEquals(1, publishingHouseDao.findAll().size(),
                        "The findAll method must return a list with size = 1")
        );

        publishingHouseDao.delete(savedPublishingHouse.getId());
    }

    @Test
    void testFindById() throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse("name");
        PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse);

        assertAll(
                () -> assertNotEquals(Optional.empty(),
                        publishingHouseDao.findById(savedPublishingHouse.getId()),
                        "The findById method for savedPublishingHouse must return a non-empty Optional"),
                () -> assertEquals(Optional.empty(),
                        publishingHouseDao.findById(Long.MAX_VALUE),
                        "The findById method for Long.MAX_VALUE must return empty Optional")
        );

        publishingHouseDao.delete(savedPublishingHouse.getId());
    }

    @Test
    void testFindAll() throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse("name");
        PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse);

        assertAll(
                () -> assertEquals(1, publishingHouseDao.findAll().size(),
                        "The findAll method must return a list with size = 1")
        );

        publishingHouseDao.delete(savedPublishingHouse.getId());
    }

    @Test
    void testUpdate() throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse("name");
        PublishingHouse publishingHouseForUpdate = new PublishingHouse("new");
        PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse);
        publishingHouseDao.update(savedPublishingHouse.getId(), publishingHouseForUpdate);

        assertAll(
                () -> assertEquals(1, publishingHouseDao.findAll().size(),
                        "The findAll method must return a list with size = 1"),
                () -> assertEquals("new",
                        publishingHouseDao.findById(savedPublishingHouse.getId()).get().getName(),
                        "New name for saved PublishingHouse must be equal 'new'")
        );

        publishingHouseDao.delete(savedPublishingHouse.getId());
    }

    @Test
    void testDelete() throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse("name");
        PublishingHouse savedPublishingHouse = publishingHouseDao.save(publishingHouse);

        assertEquals(1, publishingHouseDao.findAll().size(),
                "The findAll method must return a list with size = 1");

        publishingHouseDao.delete(savedPublishingHouse.getId());

        assertEquals(0, publishingHouseDao.findAll().size(),
                "The findAll method must return a list with size = 0");
    }
}