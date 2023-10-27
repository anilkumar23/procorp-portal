package com.procorp.community.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityChatDeleteDto {
    Long communityId;
    private String date;
    private CommunityChatHistoryResponseDto communityChatHistory;
}
