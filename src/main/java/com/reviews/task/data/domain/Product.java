package com.reviews.task.data.domain;

import com.reviews.task.data.enums.ReviewAble;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    private Provider provider;
    private boolean displayable;
    @Enumerated(EnumType.STRING)
    private ReviewAble reviewAble;
    private boolean commentsEnabled;
    private boolean votesEnabled;
    private Double averageRating;
    private int totalReviews;
    private int totalPrice;
    private int discount;
    private int payableAmount;
}