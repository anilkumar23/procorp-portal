package com.procorp.chat.resource;

import com.google.common.collect.Multimap;
import com.procorp.chat.dtos.ChatDTO;
import com.procorp.chat.dtos.ChatHistoryDTO;
import com.procorp.chat.exception.GlobalResponseDTO;
import com.procorp.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ResponseEntity<?> insertChat(@Valid @RequestBody ChatDTO chatDTO){
         return service.insertChat(chatDTO);
    }

    @PutMapping(value = "/updateLatestChat", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> updateLatestChat(@Valid @RequestBody ChatDTO chatDTO){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("updated Latest Chat Successfully")
                        .responseObj(service.updateLatestChat(chatDTO))
                        .build());
      //  return service.updateLatestChat(chatDTO);
    }

    @DeleteMapping(value = "/deleteChatMessages")
    private ResponseEntity<?> deleteChatMessages(@Valid @RequestBody ChatDTO chatDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Deleted Chat Message Successfully")
                        .responseObj(service.deleteChatByMessages(chatDTO))
                        .build());
       // return service.deleteChatByMessages(chatDTO);
    }
    @GetMapping(value = "/findChatByDate/{studentId}/{chatPersonId}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> findChatByDate(@PathVariable Long studentId, Long chatPersonId, String date){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the Chat Message By date Successfully")
                        .responseObj(service.findChatByDate(studentId, chatPersonId, date))
                        .build());
        // return service.findChatByDate(studentId, chatPersonId, date);
    }

}
