package com.procorp.community.dtos;

import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityChatDto {
    Long communityId;
    Long memberId;
    private String date;
    private CommunityChatHistoryDto communityChatHistory;
}
