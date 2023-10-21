package com.procorp.post.dto;

import lombok.Data;

@Data
public class PostShareDetailsResponseDto {
    private long memberId;
    private long postId;
    private String timestamp;
}
