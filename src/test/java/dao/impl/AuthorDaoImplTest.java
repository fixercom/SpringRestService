package dao.impl;

import config.TestPostgresContainer;
import dao.AuthorDao;
import dao.BookDao;
import dao.PublishingHouseDao;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class AuthorDaoImplTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestPostgresContainer.getInstance();
    private static final AuthorDao authorDao = AuthorDaoImpl.getInstance();
    private static final PublishingHouseDao publishingHouseDao = PublishingHouseDaoImpl.getInstance();
    public static final BookDao bookDao = BookDaoImpl.getInstance();


    @Test
    void testSave() throws SQLException {
        String authorName = "Ivan";
        Author author = new Author(authorName);
        Author savedAuthor = authorDao.save(author);

        assertAll(
                () -> assertNotNull(savedAuthor.getId(), "Saved author id must not be null"),
                () -> assertEquals(1, authorDao.findAll().size(),
                        "The findAll method must return a list with size = 1")
        );

        authorDao.delete(savedAuthor.getId());
    }

    @Test
    void testFindById() throws SQLException {
        String authorName = "Andrey";
        Author author = new Author(authorName);
        Author savedAuthor = authorDao.save(author);

        assertAll(
                () -> assertNotEquals(Optional.empty(),
                        authorDao.findById(savedAuthor.getId()),
                        "The findById method for savedAuthor must return a non-empty Optional"),
                () -> assertEquals(Optional.empty(),
                        authorDao.findById(Long.MAX_VALUE),
                        "The findById method for Long.MAX_VALUE must return empty Optional")
        );

        authorDao.delete(savedAuthor.getId());
    }

    @Test
    void testFindAll() throws SQLException {
        Author author = new Author("author1");
        Author savedAuthor = authorDao.save(author);

        assertAll(
                () -> assertEquals(1, authorDao.findAll().size(),
                        "The findAll method must return a list with size = 1")
        );

        authorDao.delete(savedAuthor.getId());
    }

    @Test
    void testUpdate() throws SQLException {
        Author author = new Author("Alexey");
        Author authorForUpdate = new Author("Ivan");

        Author savedAuthor = authorDao.save(author);
        authorDao.update(savedAuthor.getId(), authorForUpdate);

        assertAll(
                () -> assertEquals(1, authorDao.findAll().size(),
                        "The findAll method must return a list with size = 1"),
                () -> assertEquals("Ivan",
                        authorDao.findById(savedAuthor.getId()).get().getName(),
                        "New name for saved author must be equal 'Ivan'")
        );

        authorDao.delete(savedAuthor.getId());
    }

    @Test
    void testDelete() throws SQLException {
        Author savedAuthor = authorDao.save(new Author("Robert"));

        assertEquals(1, authorDao.findAll().size(),
                "The findAll method must return a list with size = 1");

        authorDao.delete(savedAuthor.getId());

        assertEquals(0, authorDao.findAll().size(),
                "The findAll method must return a list with size = 0");
    }

    @Test
    void testFindAllBooksByAuthorId() throws SQLException {
        Author savedAuthor = authorDao.save(new Author("Robert"));
        PublishingHouse savedPublishingHouse = publishingHouseDao
                .save(new PublishingHouse("Crown"));
        Book book1 = new Book("Book 1", savedPublishingHouse, List.of(savedAuthor));
        Book book2 = new Book("Book 2", savedPublishingHouse, List.of(savedAuthor));
        Book savedBook1 = bookDao.save(book1);
        Book savedBook2 = bookDao.save(book2);

        List<Book> books = authorDao.findAllBooksByAuthorId(savedAuthor.getId());

        assertAll(
                () -> assertEquals(2, books.size()),
                () -> assertEquals("Book 1", books.get(0).getName()),
                () -> assertEquals("Book 2", books.get(1).getName())
        );

        bookDao.delete(savedBook1.getId());
        bookDao.delete(savedBook2.getId());
        publishingHouseDao.delete(savedPublishingHouse.getId());
        authorDao.delete(savedAuthor.getId());
    }

}