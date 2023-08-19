package service.impl;

import config.DataSource;
import dao.AuthorDao;
import entity.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    AuthorDao authorDao;
    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    void testAddAuthor() throws SQLException {
        try (MockedStatic<DataSource> datasource = mockStatic(DataSource.class)) {

            authorService.addAuthor(new Author("name"));

            datasource.verify(DataSource::getConnection);
            verify(authorDao).save(any(), any());
        }
    }

    @Test
    void getAuthorById() {
    }

    @Test
    void getAllAuthors() {
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void deleteAuthor() {
    }
}