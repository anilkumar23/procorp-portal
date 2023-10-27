package com.procorp.community.resource;

import com.google.common.collect.Multimap;
import com.procorp.community.dtos.CommunityChatDeleteDto;
import com.procorp.community.dtos.CommunityChatDto;
import com.procorp.community.dtos.CommunityChatHistoryDto;
import com.procorp.community.service.CommunityChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("community-chat-service")
@SecurityRequirement(name = "bearerAuth")
public class CommunityChatResource {
    @Autowired
    private CommunityChatService service;

    @PostMapping(value = "/insertCommunityChat", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> insertChat(@Valid @RequestBody CommunityChatDto chatDTO){
        return service.createCommunityChat(chatDTO);
    }

    @PutMapping(value = "/updateLatestChat", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> updateLatestChat(@Valid @RequestBody CommunityChatDto chatDTO){
        return service.updateLatestChat(chatDTO);
    }

    @DeleteMapping(value = "/deleteChatMessages")
    private String deleteChatMessages(@Valid @RequestBody CommunityChatDeleteDto chatDTO){
        return service.deleteChatMessages(chatDTO);
    }
    @GetMapping(value = "/findChatByDate/{communityId}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> findChat(@PathVariable Long communityId, @PathVariable String date){
        return service.findCommunityChat(communityId, date);
    }
}
