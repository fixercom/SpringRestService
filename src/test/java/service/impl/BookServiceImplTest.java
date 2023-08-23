package service.impl;

import dao.BookDao;
import entity.Author;
import entity.Book;
import entity.PublishingHouse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookDao bookDao;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testAddBook() {
        PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
        Book book = new Book(10L, "bookName", publishingHouse);
        when(bookDao.save(book)).thenReturn(book);
        when(bookDao.findAllAuthorsByBookId(10L))
                .thenReturn(List.of(new Author(33L, "authorName")));

        bookService.addBook(book);

        InOrder inOrder = inOrder(bookDao);
        inOrder.verify(bookDao).save(book);
        inOrder.verify(bookDao).findAllAuthorsByBookId(10L);
    }

    @Test
    void testGetBookById() {
        PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
        Book book = new Book(10L, "bookName", publishingHouse);
        when(bookDao.findById(10L)).thenReturn(Optional.of(book));
        when(bookDao.findAllAuthorsByBookId(10L))
                .thenReturn(List.of(new Author("authorName")));

        bookService.getBookById(10L);

        InOrder inOrder = inOrder(bookDao);
        inOrder.verify(bookDao).findById(10L);
        inOrder.verify(bookDao).findAllAuthorsByBookId(10L);
    }

    @Test
    void testGetAllBooks() {
        when(bookDao.findAll()).thenReturn(Collections.emptyList());

        bookService.getAllBooks();

        verify(bookDao).findAll();
    }

    @Test
    void testUpdateBook() {
        PublishingHouse publishingHouse = new PublishingHouse(1L, "Crown");
        Book book = new Book(10L, "bookName", publishingHouse);
        when(bookDao.update(10L, book)).thenReturn(book);

        bookService.updateBook(10L, book);

        verify(bookDao).update(10L, book);
    }

    @Test
    void deleteBook() {
        doNothing().when(bookDao).delete(1L);

        bookService.deleteBook(1L);

        verify(bookDao).delete(1L);
    }
}