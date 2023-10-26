package com.procorp.community.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multimap;
import com.procorp.community.dtos.CommunityChatHistoryDto;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommunityChat {
    @Id
    private String chatId;
    private long communityId;
    private long memberId;
    private String date;

    @Column(columnDefinition = "jsonb", nullable = false, name = "communityChatHistory")
    @Type(JsonBinaryType.class)
    @JsonProperty("multimap_test")
    private CopyOnWriteArrayList<Multimap<String, CommunityChatHistoryDto>> communityChatHistory;

}
