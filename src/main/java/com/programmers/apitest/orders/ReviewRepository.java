package com.programmers.apitest.orders;

import java.util.Optional;

public interface ReviewRepository {
    boolean save(Long productId, Long userId, ReviewRequest request);

    Optional<Review> findByProductIdAndUserId(Long productId, Long userId);
}
