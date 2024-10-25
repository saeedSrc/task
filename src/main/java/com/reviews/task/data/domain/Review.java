package com.reviews.task.data.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;
    private String comment;
    private Integer vote;
    private Boolean approved = false;
    private LocalDateTime createdAt = LocalDateTime.now();

}
