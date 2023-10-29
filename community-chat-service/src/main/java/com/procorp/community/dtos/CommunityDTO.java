package com.procorp.community.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommunityDTO {

    Long memberId;

    String commName;

    String commDescription;

    MultipartFile groupPhoto;

    MultipartFile coverPhoto;

    String objective;

    //comm privacy settinsg

}
