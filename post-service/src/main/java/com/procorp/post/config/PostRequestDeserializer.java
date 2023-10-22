package com.procorp.post.config;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.procorp.post.dto.PostRequestDto;

import java.io.IOException;

public class PostRequestDeserializer extends JsonDeserializer<PostRequestDto> {
    @Override
    public PostRequestDto deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return new PostRequestDto();
    }
}
