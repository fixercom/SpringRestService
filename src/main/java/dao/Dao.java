package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao <K, E>{

    E save(E entity, Connection connection) throws SQLException;
    Optional<E> findById(K id, Connection connection) throws SQLException;
    List<E> findAll(Connection connection) throws SQLException;
    E update(K id, E entity, Connection connection) throws SQLException;
    void delete(K id, Connection connection) throws SQLException;

}
