package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.JacksonObjectMapper;
import entity.ApiError;
import entity.PublishingHouse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublishingHouseService;
import service.impl.PublishingHouseServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@WebServlet(value = "/publishing_houses/*")
public class PublishingHouseServlet extends HttpServlet {

    private PublishingHouseService publishingHouseService = PublishingHouseServiceImpl.getInstance();
    private final ObjectMapper objectMapper = JacksonObjectMapper.getInstance();
    private static final String PUBLISHING_HOUSE_NOT_FOUND_MESSAGE = "Publishing house with id=%d does not exist";
    private static final String CONTENT_TYPE = "application/json";
    private static final String NOT_VALID_REQUEST_URI_MESSAGE = "Not valid request uri";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String responseJson;
        ApiError error;
        if (pathInfo == null) {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            PublishingHouse publishingHouse = objectMapper.readValue(body, PublishingHouse.class);
            publishingHouse = publishingHouseService.addPublishingHouse(publishingHouse);
            resp.setStatus(201);
            responseJson = objectMapper.writeValueAsString(publishingHouse);
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
                    PublishingHouse publishingHouse = publishingHouseService.getPublishingHouseById(id);
                    responseJson = objectMapper.writeValueAsString(publishingHouse);
                    resp.setStatus(200);
                } catch (NoSuchElementException e) {
                    error = new ApiError(409, String.format(PUBLISHING_HOUSE_NOT_FOUND_MESSAGE, id));
                    responseJson = objectMapper.writeValueAsString(error);
                    resp.setStatus(409);
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                error = new ApiError(400, NOT_VALID_REQUEST_URI_MESSAGE);
                responseJson = objectMapper.writeValueAsString(error);
                resp.setStatus(400);
            }
        } else {
            List<PublishingHouse> publishingHouses = publishingHouseService.getAllPublishingHouses();
            responseJson = objectMapper.writeValueAsString(publishingHouses);
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
            publishingHouseService.deletePublishingHouse(id);
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
                PublishingHouse publishingHouse = objectMapper.readValue(body, PublishingHouse.class);
                publishingHouse = publishingHouseService.updatePublishingHouse(id, publishingHouse);
                responseJson = objectMapper.writeValueAsString(publishingHouse);
                resp.setStatus(200);
            } catch (NoSuchElementException e) {
                error = new ApiError(409, String.format(PUBLISHING_HOUSE_NOT_FOUND_MESSAGE, id));
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
