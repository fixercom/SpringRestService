package servlets;

import entity.Author;
import entity.PublishingHouse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AuthorService;
import service.PublishingHouseService;

import java.io.*;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublishingHouseServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PublishingHouseService publishingHouseService;
    @InjectMocks
    private PublishingHouseServlet publishingHouseServlet;

    @Test
    void testDoPostWhenPathInfoIsNull() throws IOException {
        String json = "{\"name\":\"testName\"}";

        when(request.getPathInfo()).thenReturn(null);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(publishingHouseService.addPublishingHouse(any(PublishingHouse.class)))
                .thenReturn(new PublishingHouse(""));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doPost(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(request).getReader();
        inOrder.verify(publishingHouseService).addPublishingHouse(any(PublishingHouse.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPostWhenPathInfoIsNotNull() throws IOException {
        when(request.getPathInfo()).thenReturn("");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doPost(request, response);

        InOrder inOrder = inOrder(request, response);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenPathInfoIsNull() throws IOException {
        when(request.getPathInfo()).thenReturn(null);
        when(publishingHouseService.getAllPublishingHouses()).thenReturn(Collections.emptyList());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService).getAllPublishingHouses();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenCorrectPathInfoWithPathVariable() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(publishingHouseService.getPublishingHouseById(1L)).thenReturn(new PublishingHouse());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService).getPublishingHouseById(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenPublishingHouseNotExist() throws IOException {
        when(request.getPathInfo()).thenReturn("/9999");
        when(publishingHouseService.getPublishingHouseById(9999L)).thenThrow(NoSuchElementException.class);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService).getPublishingHouseById(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoGetWhenPathInfoIsNotValid() throws IOException {
        when(request.getPathInfo()).thenReturn("/1f");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doGet(request, response);

        InOrder inOrder = inOrder(request, response);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void TestDoDeleteWhenSuccessful() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");

        publishingHouseServlet.doDelete(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService).deletePublishingHouse(anyLong());
        inOrder.verify(response).setStatus(anyInt());
    }

    @Test
    void TestDoDeleteWhenNotValidPathInfo() throws IOException {
        when(request.getPathInfo()).thenReturn("/5r");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doDelete(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService, never()).deletePublishingHouse(anyLong());
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPutWhenPublishingHouseForUpdateIsPresent() throws IOException {
        String json = "{\"name\":\"testName\"}";
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(publishingHouseService.updatePublishingHouse(anyLong(), any(PublishingHouse.class)))
                .thenReturn(new PublishingHouse(""));
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doPut(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService).updatePublishingHouse(anyLong(), any(PublishingHouse.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDoPutWhenPublishingHouseForUpdateIsNotPresent() throws IOException {
        String json = "{\"name\":\"testName\"}";
        when(request.getPathInfo()).thenReturn("/9999");
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(publishingHouseService.updatePublishingHouse(anyLong(), any(PublishingHouse.class)))
                .thenThrow(NoSuchElementException.class);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doPut(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService).updatePublishingHouse(anyLong(), any(PublishingHouse.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

    @Test
    void testDdoPutWhenPathInfoIsNotValid() throws IOException {
        when(request.getPathInfo()).thenReturn("/9r");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        publishingHouseServlet.doPut(request, response);

        InOrder inOrder = inOrder(request, response, publishingHouseService);
        inOrder.verify(request).getPathInfo();
        inOrder.verify(publishingHouseService,never()).updatePublishingHouse(anyLong(), any(PublishingHouse.class));
        inOrder.verify(response).setStatus(anyInt());
        inOrder.verify(response).setContentType(anyString());
        inOrder.verify(response).getWriter();
    }

}