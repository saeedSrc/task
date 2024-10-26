package com.reviews.task.services;

import com.reviews.task.constants.ErrorMessages;
import com.reviews.task.constants.RedisKeys;
import com.reviews.task.data.domain.Product;
import com.reviews.task.data.domain.User;
import com.reviews.task.data.dtos.ProductReviewSummaryDTO;
import com.reviews.task.data.dtos.ReviewDTO;
import com.reviews.task.exception.ReviewPermissionException;
import com.reviews.task.repositories.ProductRepository;
import com.reviews.task.repositories.ReviewRepository;
import com.reviews.task.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import com.reviews.task.data.domain.Review;
import com.reviews.task.data.enums.ReviewAble;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReviewService {

    private final ProductRepository productRepository;


    private final RedisService redisService;


    private final UserRepository userRepository;


    private final ReviewRepository reviewRepository;



    public  ReviewService(ReviewRepository reviewRepository, RedisService redisService, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.redisService = redisService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public Page<Product> getDisplayableProducts(Pageable pageable) {
        return productRepository.findByDisplayableTrue(pageable);
    }

    public boolean canUserReviewProduct(Long userId, Product product) {
        // اینجا جایی هست که من بر حسب مقدار reviewAble تصمیم میگیرم که ایا به کاربر اجازه review بدم یا خیر
        if (product.getReviewAble() == ReviewAble.NO_BODY) {
            return false;
        } else if (product.getReviewAble() == ReviewAble.ALL) {
            return true;
        } else {
            return Boolean.TRUE.equals(redisService.isMember(RedisKeys.PURCHASED_PRODUCTS + userId, product.getId().toString()));
        }
    }

    public boolean canUserReviewProduct(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));
        // اینجا جایی هست که من بر حسب مقدار reviewAble تصمیم میگیرم که ایا به کاربر اجازه review بدم یا خیر
        if (product.getReviewAble() == ReviewAble.NO_BODY) {
            return false;
        } else if (product.getReviewAble() == ReviewAble.ALL) {
            return true;
        } else {
            return Boolean.TRUE.equals(redisService.isMember(RedisKeys.PURCHASED_PRODUCTS + userId, product.getId().toString()));
        }
    }


    public boolean hasBeenReviewed(Long userId, Long productId) {
        List<Review> reviews = reviewRepository.findByUserIdAndProductId(userId, productId);
        return reviews.size() > 0;
    }

    public ReviewDTO addReview(Long userId, Long productId, String comment, int vote) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));

        if (!canUserReviewProduct(userId, product)) {
            throw new ReviewPermissionException(ErrorMessages.REVIEW_PERMISSION);
        }

        if (hasBeenReviewed(userId, productId)) {
            throw new ReviewPermissionException(ErrorMessages.REVIEW_ADDED_BEFORE);
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setComment(comment);
        review.setVote(vote);
        review.setApproved(false);
        Review savedReview = reviewRepository.save(review);
        return new ReviewDTO(savedReview.getUser().getUsername(), review.getProduct().getId(), review.getComment(), review.getVote(), LocalDateTime.now(), review.getApproved());
    }

    public void approveReview(Long reviewId, boolean approved) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.REVIEW_NOT_FOUND));

        review.setApproved(approved);
        if(approved) { // adjust product average rating and total rating count
            Product product = review.getProduct();
            int totalReviews = product.getTotalReviews();
            var currentAverageRating = product.getAverageRating();
            double newVote = review.getVote();
            double newAverageRating = ((currentAverageRating * totalReviews) + newVote) / (totalReviews + 1);
            product.setAverageRating(newAverageRating);
            product.setTotalReviews(totalReviews + 1);
            productRepository.save(product);
        }


        reviewRepository.save(review);
    }

    public void updateProductReviewSettings(Long productId, ReviewAble reviewAble, boolean commentsEnabled, boolean votesEnabled) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));

        product.setReviewAble(reviewAble);
        product.setCommentsEnabled(commentsEnabled);
        product.setVotesEnabled(votesEnabled);

        productRepository.save(product);
    }


    public ProductReviewSummaryDTO getReviewSummary(Long productId) {
        List<Review> lastThreeReviews =  reviewRepository.findTop3ByProductIdOrderByCreatedAtDesc(productId);
        if(lastThreeReviews.size()> 0) {
            return new ProductReviewSummaryDTO(lastThreeReviews);
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));
        return new ProductReviewSummaryDTO(product);
    }
}

