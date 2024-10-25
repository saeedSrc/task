package com.reviews.task.data.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {

    private String username;
    private Long productId;
    private String comment;
    private int vote;
    private LocalDateTime createdAt;
    private boolean approved;

    public ReviewDTO(String username, Long productId, String comment, int vote, LocalDateTime createdAt, boolean approved) {
        this.username = username;
        this.productId = productId;
        this.comment = comment;
        this.vote = vote;
        this.createdAt = createdAt;
        this.approved = approved;
    }
}
