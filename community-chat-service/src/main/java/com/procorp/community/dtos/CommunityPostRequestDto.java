package com.procorp.community.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostRequestDto {
    private long communityId;
    private long memberId;
    private String postDescription;
    private MultipartFile media;
    private String mediaType;
}
