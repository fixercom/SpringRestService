package dao.impl;

import dao.PublishingHouseDao;
import entity.PublishingHouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublishingHouseDaoImpl implements PublishingHouseDao {

    private static final PublishingHouseDaoImpl INSTANCE = new PublishingHouseDaoImpl();
    private static final String SAVE_SQL = "INSERT INTO publish_houses (name) VALUES ( ? )";
    private static final String FIND_BY_ID_SQL = "SELECT id, name FROM publish_houses WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT id, name FROM publish_houses";
    private static final String UPDATE_SQL = "UPDATE publish_houses SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM publish_houses WHERE id = ?";

    private PublishingHouseDaoImpl() {
    }

    public PublishingHouseDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public PublishingHouse save(PublishingHouse publishingHouse, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, publishingHouse.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                publishingHouse.setId(generatedKeys.getLong("id"));
            }
        }
        return publishingHouse;
    }

    @Override
    public Optional<PublishingHouse> findById(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PublishingHouse publishingHouse = null;
            if (resultSet.next()) {
                publishingHouse = new PublishingHouse();
                publishingHouse.setId(resultSet.getLong("id"));
                publishingHouse.setName(resultSet.getString("name"));
            }
            return Optional.ofNullable(publishingHouse);
        }
    }

    @Override
    public List<PublishingHouse> findAll(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PublishingHouse> publishingHouses = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                publishingHouses.add(new PublishingHouse(id, name));
            }
            return publishingHouses;
        }
    }

    @Override
    public PublishingHouse update(Long id, PublishingHouse publishingHouse, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, publishingHouse.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
        return publishingHouse;
    }

    @Override
    public void delete(Long id, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
