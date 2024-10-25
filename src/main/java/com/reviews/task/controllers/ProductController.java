package com.reviews.task.controllers;

import com.reviews.task.data.domain.Product;
import com.reviews.task.data.dtos.ProductDTO;
import com.reviews.task.data.dtos.ProductReviewSummaryDTO;
import com.reviews.task.data.dtos.ReviewDTO;
import com.reviews.task.data.request.ReviewRequest;
import com.reviews.task.services.ProductService;
import com.reviews.task.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getDisplayableProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
         Page<Product> products = reviewService.getDisplayableProducts(pageable);
        return ResponseEntity.ok(products.map(productService::convertToProductDto));
    }

    @GetMapping("/{productId}/review-permission")
    public ResponseEntity<Boolean> checkReviewPermission(
            @PathVariable Long productId, @RequestParam Long userId) {
        boolean canReview = reviewService.canUserReviewProduct(userId, productId);
        return ResponseEntity.ok(canReview);
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable Long productId,
            @RequestBody ReviewRequest reviewRequest) {
        ReviewDTO review = reviewService.addReview(reviewRequest.getUserId(), productId, reviewRequest.getComment(), reviewRequest.getVote());
        return ResponseEntity.ok(review);
    }


    @GetMapping("/{productId}/reviews/summary")
    public ResponseEntity<ProductReviewSummaryDTO> getReviewSummary(@PathVariable Long productId) {
        ProductReviewSummaryDTO productReviewSummaryDTO =  reviewService.getReviewSummary(productId);
         return ResponseEntity.ok(productReviewSummaryDTO);
    }
}

