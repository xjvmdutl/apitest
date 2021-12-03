package com.programmers.apitest.orders;

import com.programmers.apitest.configures.web.SimplePageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll(SimplePageRequest simplePageRequest, Long userId) {
        return orderRepository.findAll(simplePageRequest,userId);
    }

    public Optional<Order> findById(Long id, Long userId) {
        return orderRepository.findById(id,userId);
    }

    @Transactional
    public Boolean accept(Long id, Long userId) {
        Order order = orderRepository.findById(id,userId).get();
        if(order.getState().equals(State.REQUESTED)){
            return orderRepository.updateByAccept(id);
        }
        return false;
    }

    @Transactional
    public Boolean reject(Long id, Long userId, RejectRequest request) {
        Order order = orderRepository.findById(id,userId).get();
        if(order.getState().equals(State.REQUESTED)){
            return orderRepository.updateByReject(id,request);
        }
        return false;
    }

    @Transactional
    public Boolean shipping(Long id, Long userId) {
        Order order = orderRepository.findById(id,userId).get();
        if(order.getState().equals(State.ACCEPTED)){
            return orderRepository.updateByShipping(id);
        }
        return false;
    }

    @Transactional
    public Boolean complete(Long id, Long userId) {
        Order order = orderRepository.findById(id,userId).get();
        if(order.getState().equals(State.SHIPPING)){
            return orderRepository.updateByComplete(id);
        }
        return false;
    }
}
