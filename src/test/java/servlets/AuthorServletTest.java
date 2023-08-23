package servlets;

import entity.Author;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AuthorService;

import java.io.*;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private AuthorServlet authorServlet;

    @Test
    void testDoPostWhenPathInfoIsNull() throws IOException {
        String json = "{\"name\":\"testName\"}";

        when(request.getPathInfo()).thenReturn(null);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(authorService.addAuthor(any(Author.class))).thenReturn(new Author(""));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        authorServlet.doPost(request, response);

        InOrder inOrder = Mockito.inOrder(request, response, authorService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(request).getReader();
        inOrder.verify(authorService).addAuthor(any(Author.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPostWhenPathInfoIsNotNull() throws IOException {
        when(request.getPathInfo()).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        authorServlet.doPost(request, response);

        InOrder inOrder = Mockito.inOrder(request,response);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenCorrectPathInfoWithPathVariable() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(authorService.getAuthorById(1L)).thenReturn(new Author());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        authorServlet.doGet(request, response);

        InOrder inOrder = Mockito.inOrder(request, response, authorService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(authorService).getAuthorById(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenPathInfoIsNull() throws IOException {
        when(request.getPathInfo()).thenReturn(null);
        when(authorService.getAllAuthors()).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        authorServlet.doGet(request, response);

        InOrder inOrder = Mockito.inOrder(request, response, authorService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(authorService).getAllAuthors();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void doDelete() {
    }

    @Test
    void doPut() {
    }
}