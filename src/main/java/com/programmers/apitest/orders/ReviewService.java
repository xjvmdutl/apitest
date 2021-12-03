package com.programmers.apitest.orders;

import com.programmers.apitest.products.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Optional<Review> review(Long id, Long userId, ReviewRequest request) {
        if(isValid(id,userId,request)){
            Order order = orderRepository.findById(id,userId).get();
            boolean insert = reviewRepository.save(order.getProductId(),userId,request);

            if(insert){
                Optional<Review> review = reviewRepository.findByProductIdAndUserId(order.getProductId(),userId);
                boolean updateOk = productRepository.updateByReview(review.get().getProductId());
                boolean update = orderRepository.updateByReviewId(order.getSeq(),review.get().getSeq());
                if(updateOk && update){
                    return review;
                }
                throw new RuntimeException("Update Failed");
            }
            throw new RuntimeException("Insert Failed");
        }
        throw new InvalidParameterException();
    }

    private boolean isValid(Long id, Long userId, ReviewRequest request) {
        Optional<Order> order = orderRepository.findById(id,userId);
        if(!order.isPresent()){
            throw new InvalidParameterException();
        }
        if(order.get().getReview() != null){
            throw new InvalidParameterException("Could not write review for order "+ id + " because have already written");
        }
        if(!order.get().getState().equals(State.COMPLETED)){
            throw new InvalidParameterException("Could not write review for order "+ id + " because state(REQUESTED) is not allowed");
        }
        return true;
    }
}
