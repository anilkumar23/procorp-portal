package com.procorp.community.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityChatResponseDto {
    Long communityId;
    Long memberId;
    private String date;
    private CopyOnWriteArrayList<CommunityChatHistoryResponseDto> communityChatHistory;
}
