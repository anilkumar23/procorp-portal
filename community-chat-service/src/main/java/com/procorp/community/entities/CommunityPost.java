package com.procorp.community.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "community_posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityPost {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private long postOwner;// => memberId
    private String timestamp;
    private String postDescription;
    private String mediaName;
    private String mediaType;
    private String mediaPath;

    private long communityId;
}
