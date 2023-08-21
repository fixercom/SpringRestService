package config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapper {

    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private JacksonObjectMapper() {
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }

}
