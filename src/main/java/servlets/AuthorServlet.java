package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Author;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthorService;
import service.impl.AuthorServiceImpl;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(value = "/authors")
public class AuthorServlet extends HttpServlet {

    private final AuthorService authorService = AuthorServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Author author = new ObjectMapper().readValue(body, Author.class);
        author = authorService.addAuthor(author);
        System.out.println(author);
    }
}
