package service.impl;

import config.DataSource;
import dao.PublishingHouseDao;
import dao.impl.PublishingHouseDaoImpl;
import entity.PublishingHouse;
import exception.DaoException;
import service.PublishingHouseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PublishingHouseServiceImpl implements PublishingHouseService {

    private static final PublishingHouseServiceImpl INSTANCE = new PublishingHouseServiceImpl();
    private PublishingHouseDao publishingHouseDao = PublishingHouseDaoImpl.getInstance();

    private PublishingHouseServiceImpl() {
    }

    public static PublishingHouseServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public PublishingHouse addPublishingHouse(PublishingHouse publishingHouse) {
        try (Connection connection = DataSource.getConnection()) {
            return publishingHouseDao.save(publishingHouse, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public PublishingHouse getPublishingHouseById(Long id) {
        try (Connection connection = DataSource.getConnection()) {
            return publishingHouseDao.findById(id, connection).orElseThrow();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PublishingHouse> getAllPublishingHouses() {
        try (Connection connection = DataSource.getConnection()) {
            return publishingHouseDao.findAll(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public PublishingHouse updatePublishingHouse(Long id, PublishingHouse publishingHouse) {
        try (Connection connection = DataSource.getConnection()) {
            return publishingHouseDao.update(id, publishingHouse, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deletePublishingHouse(Long id) {
        try (Connection connection = DataSource.getConnection()) {
            publishingHouseDao.delete(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
