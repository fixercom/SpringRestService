package service.impl;

import dao.PublishingHouseDao;
import dao.impl.PublishingHouseDaoImpl;
import entity.PublishingHouse;
import service.PublishingHouseService;

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
        return publishingHouseDao.save(publishingHouse);
    }

    @Override
    public PublishingHouse getPublishingHouseById(Long id) {
        return publishingHouseDao.findById(id).orElseThrow();
    }

    @Override
    public List<PublishingHouse> getAllPublishingHouses() {
        return publishingHouseDao.findAll();
    }

    @Override
    public PublishingHouse updatePublishingHouse(Long id, PublishingHouse publishingHouse) {
        return publishingHouseDao.update(id, publishingHouse);
    }

    @Override
    public void deletePublishingHouse(Long id) {
        publishingHouseDao.delete(id);
    }

}
