package com.example.order.service.impl;

import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.service.KafkaProducer;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public Order placeOrder(Order order) {
        order.setOrderStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        kafkaProducer.sendOrder(savedOrder);
        return savedOrder;
    }

}
