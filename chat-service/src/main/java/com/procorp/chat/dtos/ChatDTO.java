package com.procorp.chat.dtos;

import com.google.common.collect.Multimap;
import lombok.*;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDTO {
    private long studentId;
    private long chatPersonId;
    private String date;
    private CopyOnWriteArrayList<Multimap<String, ChatHistoryDTO>> chatHistory;
}
