package service.impl;

import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;
import entity.Author;
import entity.Book;
import service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private static final AuthorServiceImpl INSTANCE = new AuthorServiceImpl();
    private AuthorDao authorDao = AuthorDaoImpl.getInstance();

    private AuthorServiceImpl() {
    }

    public static AuthorServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Author addAuthor(Author author) {
        return authorDao.save(author);
    }

    @Override
    public Author getAuthorById(Long id) {
        Author author = authorDao.findById(id).orElseThrow();
        List<Book> authorBooks = authorDao.findAllBooksByAuthorId(id);
        author.setBooks(authorBooks);
        return author;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        return authorDao.update(id, author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorDao.delete(id);
    }

}
