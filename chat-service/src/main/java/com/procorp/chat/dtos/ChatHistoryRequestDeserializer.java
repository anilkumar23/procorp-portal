package com.procorp.chat.dtos;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ChatHistoryRequestDeserializer extends JsonDeserializer<ChatHistoryRequestDTO> {
    @Override
    public ChatHistoryRequestDTO deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return new ChatHistoryRequestDTO();
    }
}
