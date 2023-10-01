package com.procorp.chat.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multimap;
import com.procorp.chat.dtos.ChatHistoryDTO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Chat {

    @Id
    private String chatId;
    private long studentId;
    private long chatPersonId;
    private String date;

    @Column(columnDefinition = "jsonb", nullable = false, name = "chatHistory")
    @Type(JsonBinaryType.class)
    @JsonProperty("multimap_test")
    private CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> chatHistory;

}