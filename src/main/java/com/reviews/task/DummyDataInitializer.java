package com.reviews.task;

import com.reviews.task.data.domain.Product;
import com.reviews.task.data.domain.Provider;
import com.reviews.task.data.domain.Review;
import com.reviews.task.data.domain.User;
import com.reviews.task.data.enums.ReviewAble;
import com.reviews.task.repositories.ProductRepository;
import com.reviews.task.repositories.ProviderRepository;
import com.reviews.task.repositories.ReviewRepository;
import com.reviews.task.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DummyDataInitializer implements CommandLineRunner {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save providers
        Provider providerA = new Provider("Provider A", "Description A");
        Provider providerB = new Provider("Provider B", "Description B");
        providerRepository.save(providerA);
        providerRepository.save(providerB);

        // Create and save users
        User user1 = new User("user1", "user1@example.com");
        User user2 = new User("user2", "user2@example.com");
        userRepository.save(user1);
        userRepository.save(user2);

        // Create and save products
        Product productA = new Product();
        productA.setName("Product A");
        productA.setDescription("Description A");
        productA.setProvider(providerA);
        productA.setDisplayable(true);
        productA.setReviewAble(ReviewAble.ALL);
        productA.setCommentsEnabled(true);
        productA.setVotesEnabled(true);
        productA.setAverageRating(0.0);
        productA.setTotalReviews(0);
        productA.setTotalPrice(100);
        productA.setPayableAmount(80);
        productA.setDiscount(20);
        productRepository.save(productA);

        Product productB = new Product();
        productB.setName("Product B");
        productB.setDescription("Description B");
        productB.setProvider(providerB);
        productB.setDisplayable(true);
        productB.setReviewAble(ReviewAble.CUSTOM);
        productB.setCommentsEnabled(true);
        productB.setVotesEnabled(true);
        productB.setAverageRating(0.0);
        productB.setTotalReviews(0);
        productA.setTotalPrice(2500);
        productA.setPayableAmount(2500);
        productA.setDiscount(0);
        productRepository.save(productB);


        Product productc = new Product();
        productc.setName("Product B");
        productc.setDescription("Description B");
        productc.setProvider(providerB);
        productc.setDisplayable(true);
        productc.setReviewAble(ReviewAble.CUSTOM);
        productc.setCommentsEnabled(true);
        productc.setVotesEnabled(true);
        productc.setAverageRating(0.0);
        productc.setTotalReviews(0);
        productc.setTotalPrice(10000);
        productc.setPayableAmount(2500);
        productc.setDiscount(7500);
        productRepository.save(productc);

        // Create and save reviews
        Review review1 = new Review();
        review1.setProduct(productB);
        review1.setUser(user1);
        review1.setComment("Great product!");
        review1.setVote(5);
        review1.setApproved(true);
        review1.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setProduct(productB);
        review2.setUser(user2);
        review2.setComment("Not bad.");
        review2.setVote(4);
        review2.setApproved(true);
        review2.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review2);
    }
}

