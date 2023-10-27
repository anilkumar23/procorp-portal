package com.procorp.community.dtos;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CommunityChatHistoryDeserializer extends JsonDeserializer<CommunityChatHistoryDto> {
    @Override
    public CommunityChatHistoryDto deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return new CommunityChatHistoryDto();
    }
}
