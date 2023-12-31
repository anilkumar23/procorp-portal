package com.procorp.chat.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestFrom;

    private Long requestTo;

    private String friendRequestStatus;

    private Long blockedBy;


    public FriendRequest(Long requestFrom, Long requestTo, String friendRequestStatus, Long blockedBy) {
        this.requestFrom = requestFrom;
        this.requestTo = requestTo;
        this.friendRequestStatus = friendRequestStatus;
        this.blockedBy = blockedBy;
     }
}
