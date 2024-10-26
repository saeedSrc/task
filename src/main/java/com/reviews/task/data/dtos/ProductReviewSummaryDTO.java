package com.reviews.task.data.dtos;

import com.reviews.task.data.domain.Product;
import com.reviews.task.data.domain.Review;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductReviewSummaryDTO {
    private Long productId;
    private String productName;
    private Double averageRating;
    private Integer totalReviews;
    private List<ReviewDTO> lastThreeReviews = new ArrayList<>();

    // Constructor to populate all fields
    public ProductReviewSummaryDTO(List<Review> lastThreeReviews) {
            Product product = lastThreeReviews.get(0).getProduct();
            this.productId = product.getId();
            this.productName = product.getName(); // Example field
            this.averageRating = product.getAverageRating();
            this.totalReviews = product.getTotalReviews();


        for (Review review : lastThreeReviews) {
            String comment = review.getComment();
            String username = review.getUser().getUsername();
            Integer vote = review.getVote();
            ReviewDTO reviewSummary = new ReviewDTO(username, review.getProduct().getId(), comment, vote, review.getCreatedAt(), review.getApproved());
            this.lastThreeReviews.add(reviewSummary);
        }
    }

    public ProductReviewSummaryDTO(Product product) {
        this.productId = product.getId();
        this.productName = product.getName(); // Example field
        this.averageRating = product.getAverageRating();
        this.totalReviews = product.getTotalReviews();
    }
}
