package service.impl;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import entity.Author;
import entity.Book;
import service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private static final BookServiceImpl INSTANCE = new BookServiceImpl();
    private BookDao bookDao = BookDaoImpl.getInstance();

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Book addBook(Book book) {
        book = bookDao.save(book);
        List<Author> authors = bookDao.findAllAuthorsByBookId(book.getId());
        book.setAuthors(authors);
        return book;
    }

    @Override
    public Book getBookById(Long id) {
        Book book = bookDao.findById(id).orElseThrow();
        List<Author> authors = bookDao.findAllAuthorsByBookId(book.getId());
        book.setAuthors(authors);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book updateBook(Long id, Book book) {
        return bookDao.update(id, book);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }
}
