package com.procorp.chat.resource;

import com.google.common.collect.Multimap;
import com.procorp.chat.dtos.ChatDTO;
import com.procorp.chat.dtos.ChatHistoryDTO;
import com.procorp.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("chat-service")
@SecurityRequirement(name = "bearerAuth")
public class ChatResource {
    @Autowired
    private ChatService service;

    @PostMapping(value = "/insertChat", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> insertChat(@Valid @RequestBody ChatDTO chatDTO){
        return service.insertChat(chatDTO);
    }

    @PutMapping(value = "/updateLatestChat", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ChatDTO updateLatestChat(@Valid @RequestBody ChatDTO chatDTO){
        return service.updateLatestChat(chatDTO);
    }

    @DeleteMapping(value = "/deleteChatMessages")
    private String deleteChatMessages(@Valid @RequestBody ChatDTO chatDTO){
        return service.deleteChatByMessages(chatDTO);
    }
    @GetMapping(value = "/findChatByDate/{studentId}/{chatPersonId}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    private CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> findChat(@PathVariable Long studentId, @PathVariable Long chatPersonId, @PathVariable String date){
        return service.findChat(studentId, chatPersonId, date);
    }

}
