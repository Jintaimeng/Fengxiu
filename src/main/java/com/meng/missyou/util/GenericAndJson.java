package com.meng.missyou.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meng.missyou.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericAndJson {
    private static ObjectMapper mapper;

    public static <T> String objectToJson(T o) {
        try {
            return GenericAndJson.mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    public static <T> T jsonToObject(String s, Class<T> classT) {
        try {
            if (s == null) {
                return null;
            }
            return GenericAndJson.mapper.readValue(s, classT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }
}
