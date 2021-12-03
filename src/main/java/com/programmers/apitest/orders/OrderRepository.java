package com.programmers.apitest.orders;

import com.programmers.apitest.configures.web.SimplePageRequest;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll(SimplePageRequest simplePageRequest, Long userId);

    Optional<Order> findById(Long id, Long userId);

    boolean updateByReviewId(Long id,Long reviewId);

    Boolean updateByAccept(Long id);

    Boolean updateByReject(Long id, RejectRequest request);

    Boolean updateByShipping(Long id);

    Boolean updateByComplete(Long id);
}
