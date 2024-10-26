package com.reviews.task.services;

import com.reviews.task.constants.ErrorMessages;
import com.reviews.task.data.domain.Product;
import com.reviews.task.data.dtos.ProductDTO;
import com.reviews.task.repositories.ProductRepository;
import com.reviews.task.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private ProductRepository productRepository;

    public ProductDTO convertToProductDto(Product product) {
        ProductDTO productDto = new ProductDTO();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setDisplayable(product.isDisplayable());
        productDto.setAverageRating(product.getAverageRating());
        productDto.setTotalReviews(product.getTotalReviews());

        productDto.setReviewable(product.getReviewAble());
        productDto.setTotalPrice(product.getTotalPrice());
        productDto.setPayableAmount(product.getPayableAmount());
        productDto.setDiscount(product.getDiscount());

        return productDto;
    }

    @Transactional
    public void setProductDisplayable(Long productId, boolean displayable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));
        product.setDisplayable(displayable);
        productRepository.save(product);
    }

}
