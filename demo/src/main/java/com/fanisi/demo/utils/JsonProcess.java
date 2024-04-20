package com.fanisi.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonProcess {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String json(Object object)
    {
        try {
            return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String strinJson(Object object)
    {
        try {
            return this.objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
