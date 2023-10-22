package com.procorp.post.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private long memberId;
    private String postDescription;
    private MultipartFile media;
    private String mediaType;
}
