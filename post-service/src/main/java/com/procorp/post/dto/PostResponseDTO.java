package com.procorp.post.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class PostResponseDTO {
    private long postId;
    private long memberId;
    private String timestamp;
    private String postDescription;
    private String mediaType;
    private String mediaPath;
}
