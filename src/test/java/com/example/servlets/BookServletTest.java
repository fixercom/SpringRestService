package com.example.servlets;

import com.example.servlets.BookServlet;
import com.example.entity.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.service.BookService;

import java.io.*;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookServlet bookServlet;

    @Test
    void testDoPostWhenPathInfoIsNull() throws IOException {
        String json = "{\"name\":\"testName\"}";

        when(request.getPathInfo()).thenReturn(null);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(bookService.addBook(any(Book.class))).thenReturn(new Book());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doPost(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(request).getReader();
        inOrder.verify(bookService).addBook(any(Book.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPostWhenPathInfoIsNotNull() throws IOException {
        when(request.getPathInfo()).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doPost(request, response);

        InOrder inOrder = inOrder(request, response);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenPathInfoIsNull() throws IOException {
        when(request.getPathInfo()).thenReturn(null);
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService).getAllBooks();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenCorrectPathInfoWithPathVariable() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(bookService.getBookById(1L)).thenReturn(new Book());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService).getBookById(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenBookNotExist() throws IOException {
        when(request.getPathInfo()).thenReturn("/9999");
        when(bookService.getBookById(9999L)).thenThrow(NoSuchElementException.class);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService).getBookById(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenPathInfoIsNotValid() throws IOException {
        when(request.getPathInfo()).thenReturn("/1f");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void TestDoDeleteWhenSuccessful() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");

        bookServlet.doDelete(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService).deleteBook(anyLong());
        inOrder.verify(response).setStatus(anyInt());
    }

    @Test
    void TestDoDeleteWhenNotValidPathInfo() throws IOException {
        when(request.getPathInfo()).thenReturn("/5r");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doDelete(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService, never()).deleteBook(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPutWhenBookForUpdateIsPresent() throws IOException {
        String json = "{\"name\":\"testName\"}";
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(new Book());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doPut(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService).updateBook(anyLong(), any(Book.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPutWhenBookForUpdateIsNotPresent() throws IOException {
        String json = "{\"name\":\"testName\"}";
        when(request.getPathInfo()).thenReturn("/9999");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(bookService.updateBook(anyLong(), any(Book.class))).thenThrow(NoSuchElementException.class);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doPut(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService).updateBook(anyLong(), any(Book.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDdoPutWhenPathInfoIsNotValid() throws IOException {
        when(request.getPathInfo()).thenReturn("/9r");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        bookServlet.doPut(request, response);

        InOrder inOrder = inOrder(request, response, bookService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(bookService, never()).updateBook(anyLong(), any(Book.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

}