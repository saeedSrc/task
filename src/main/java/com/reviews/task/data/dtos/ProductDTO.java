package com.reviews.task.data.dtos;


import com.reviews.task.data.enums.ReviewAble;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private int totalPrice;
    private int discount;
    private int payableAmount;
    private boolean displayable;
    @Enumerated(EnumType.STRING)
    private ReviewAble reviewable;

    private double averageRating;
    private int totalReviews;

    public ProductDTO() {}

}

