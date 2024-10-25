package com.reviews.task.controllers;

import com.reviews.task.data.enums.ReviewAble;
import com.reviews.task.services.ProductService;
import com.reviews.task.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;

    @PutMapping("/reviews/{reviewId}/approve")
    public ResponseEntity<?> approveReview(@PathVariable Long reviewId, @RequestParam boolean approved) {
        reviewService.approveReview(reviewId, approved);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/products/{productId}/review-settings")
    public ResponseEntity<?> updateReviewSettings(
            @PathVariable Long productId,
            @RequestParam ReviewAble reviewAble,
            @RequestParam boolean commentsEnabled,
            @RequestParam boolean votesEnabled) {
        reviewService.updateProductReviewSettings(productId, reviewAble, commentsEnabled, votesEnabled);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/products/{productId}/display")
    public ResponseEntity<String> toggleProductDisplay(@PathVariable Long productId, @RequestParam boolean displayable) {
        productService.setProductDisplayable(productId, displayable);
        String message = displayable ? "Product is now displayable" : "Product is now hidden";
        return ResponseEntity.ok(message);
    }
}

