package dao.impl;

import config.TestDataSource;
import dao.AuthorDao;
import entity.Author;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class AuthorDaoImplTest {

    private static final AuthorDao authorDao = AuthorDaoImpl.getInstance();

    @Test
    void save() throws SQLException {
        try (Connection connection = TestDataSource.getConnection()){
            Author author = new Author("Ivan");
            author = authorDao.save(author, connection);
            System.out.println(author.getId());
        }
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}