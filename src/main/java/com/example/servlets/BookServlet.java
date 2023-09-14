package com.example.servlets;

import com.example.service.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.config.JacksonObjectMapper;
import com.example.entity.ApiError;
import com.example.entity.Book;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.service.BookService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@WebServlet(value = "/books/*")
public class BookServlet extends HttpServlet {

    private BookService bookService = BookServiceImpl.getInstance();
    private final ObjectMapper objectMapper = JacksonObjectMapper.getInstance();
    private static final String BOOK_NOT_FOUND_MESSAGE = "Book with id=%d does not exist";
    private static final String CONTENT_TYPE = "application/json";
    private static final String NOT_VALID_REQUEST_URI_MESSAGE = "Not valid request uri";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String responseJson;
        ApiError error;
        if (pathInfo == null) {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Book book = objectMapper.readValue(body, Book.class);
            book = bookService.addBook(book);
            resp.setStatus(201);
            responseJson = objectMapper.writeValueAsString(book);
        } else {
            error = new ApiError(400, NOT_VALID_REQUEST_URI_MESSAGE);
            responseJson = objectMapper.writeValueAsString(error);
            resp.setStatus(400);
        }
        resp.setContentType(CONTENT_TYPE);
        resp.getWriter().append(responseJson);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String responseJson;
        ApiError error;
        if (pathInfo != null) {
            try {
                Long id = Long.parseLong(pathInfo.split("/")[1]);
                try {
                    Book book = bookService.getBookById(id);
                    responseJson = objectMapper.writeValueAsString(book);
                    resp.setStatus(200);
                } catch (NoSuchElementException e) {
                    error = new ApiError(409, String.format(BOOK_NOT_FOUND_MESSAGE, id));
                    responseJson = objectMapper.writeValueAsString(error);
                    resp.setStatus(409);
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                error = new ApiError(400, NOT_VALID_REQUEST_URI_MESSAGE);
                responseJson = objectMapper.writeValueAsString(error);
                resp.setStatus(400);
            }
        } else {
            List<Book> books = bookService.getAllBooks();
            responseJson = objectMapper.writeValueAsString(books);
            resp.setStatus(200);
        }
        resp.setContentType(CONTENT_TYPE);
        resp.getWriter().append(responseJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String responseJson;
        ApiError error;
        try {
            Long id = Long.parseLong(pathInfo.split("/")[1]);
            bookService.deleteBook(id);
            resp.setStatus(200);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            error = new ApiError(400, NOT_VALID_REQUEST_URI_MESSAGE);
            responseJson = objectMapper.writeValueAsString(error);
            resp.setStatus(400);
            resp.setContentType(CONTENT_TYPE);
            resp.getWriter().append(responseJson);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String responseJson;
        ApiError error;
        try {
            Long id = Long.parseLong(pathInfo.split("/")[1]);
            try {
                String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                Book book = objectMapper.readValue(body, Book.class);
                book = bookService.updateBook(id, book);
                responseJson = objectMapper.writeValueAsString(book);
                resp.setStatus(200);
            } catch (NoSuchElementException e) {
                error = new ApiError(409, String.format(BOOK_NOT_FOUND_MESSAGE, id));
                responseJson = objectMapper.writeValueAsString(error);
                resp.setStatus(409);
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            error = new ApiError(400, NOT_VALID_REQUEST_URI_MESSAGE);
            responseJson = objectMapper.writeValueAsString(error);
            resp.setStatus(400);
        }
        resp.setContentType(CONTENT_TYPE);
        resp.getWriter().append(responseJson);
    }

}
