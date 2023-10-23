package com.procorp.chat.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
public class CommunityDTO {

    Long memberId;

    String commName;

    String commDescription;;

    String groupPhoto;

    //comm privacy settinsg

}
