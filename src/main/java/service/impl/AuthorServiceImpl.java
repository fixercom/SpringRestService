package service.impl;

import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;
import entity.Author;
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
        return authorDao.findById(id).orElseThrow();
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
