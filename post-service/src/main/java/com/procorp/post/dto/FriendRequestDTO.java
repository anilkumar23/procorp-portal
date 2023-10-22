package com.procorp.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class FriendRequestDTO {
    private Long id;

    private Long requestFrom;

    private Long requestTo;

    private String friendRequestStatus;

    private Long blockedBy;
}
