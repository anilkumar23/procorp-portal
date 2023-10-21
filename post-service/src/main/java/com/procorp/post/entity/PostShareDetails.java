package com.procorp.post.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostShareDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shareId;

    private long memberId;
    private long postId;
    private String timeStamp;

}
