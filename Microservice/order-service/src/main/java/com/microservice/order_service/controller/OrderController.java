package com.microservice.order_service.controller;

import com.microservice.order_service.feignclient.ProductServiceClient;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.model.OrderMessage;
import com.microservice.order_service.model.Product;
import com.microservice.order_service.repo.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.System.out;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private ProductServiceClient productServiceClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        Order savedOrder = orderRepository.save(order);
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderId(savedOrder.getId());
        orderMessage.setProductId(savedOrder.getProductId());
        orderMessage.setQuantity(savedOrder.getQuantity());

        rabbitTemplate.convertAndSend("orderQueue", orderMessage);
        out.println("@@@@@@@@@@@@@@@@@-Order sent to queeue - "+ orderMessage);

        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}