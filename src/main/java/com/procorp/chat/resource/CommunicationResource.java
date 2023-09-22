//package com.procorp.chat.resource;
//
//import com.procorp.chat.dtos.CommunicationDTO;
//import com.procorp.chat.entities.Communication;
//import com.procorp.chat.service.ChatService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("chat-service")
//public class CommunicationResource {
//    @Autowired
//    private ChatService service;
//
//    @PostMapping(value = "/insertChat", consumes = MediaType.APPLICATION_JSON_VALUE)
//    private void insertChat(@Valid @RequestBody CommunicationDTO communicationDTO){
//        service.insertChat(communicationDTO);
//    }
//
//}
