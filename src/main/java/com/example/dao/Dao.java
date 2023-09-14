package com.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    E save(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    E update(K id, E entity);

    void delete(K id);

}
