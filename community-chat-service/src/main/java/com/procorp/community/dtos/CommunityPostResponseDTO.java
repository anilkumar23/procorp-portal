package com.procorp.community.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityPostResponseDTO {
    private long postId;
    private long memberId;
    private String timestamp;
    private String postDescription;
    private String mediaType;
    private String mediaPath;
    private long communityId;
    private int likeCount;
    private int commentCount;
    private String communityName;
    private String communityPhoto;
}
