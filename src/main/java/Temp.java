import config.DataSource;
import dao.BookDao;
import dao.impl.BookDaoImpl;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Temp {
    public static void main(String[] args) throws SQLException {
        BookDao bookDao = BookDaoImpl.getInstance();

        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(2L);

        Author author1 = new Author();
        author1.setId(1L);
        Author author2 = new Author();
        author2.setId(3L);

        Book book = new Book();
        book.setName("Super book");
        book.setPublishingHouse(publishingHouse);
        book.setAuthors(List.of(author1, author2));

        try (Connection connection = DataSource.getConnection()) {
            bookDao.save(book, connection);
            System.out.println(book);

            System.out.println(bookDao.findById(book.getId(), connection).orElse(null));
        }
    }
}
