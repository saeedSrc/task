package com.reviews.task.repositories;

import com.reviews.task.data.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findTop3ByProductIdOrderByCreatedAtDesc(Long productId);

    List<Review> findByUserIdAndProductId(Long userId, Long productId);
}

