package com.reviews.task.data.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long userId;
    private String comment;
    private int vote;
    public ReviewRequest() {}
}
