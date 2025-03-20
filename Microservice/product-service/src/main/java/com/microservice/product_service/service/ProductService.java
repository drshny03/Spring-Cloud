package com.microservice.product_service.service;

import com.microservice.product_service.model.OrderMessage;
import com.microservice.product_service.model.Product;
import com.microservice.product_service.repo.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @RabbitListener(queues = "orderQueue")
    public void receiveOrder(OrderMessage orderMessage){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@-Received order: " + orderMessage);

        Product product = productRepository.findById(orderMessage.getProductId()).orElseThrow(()-> new RuntimeException("product not found"));

        // Update the product stock
        int newStock = product.getStock() - orderMessage.getQuantity();
        product.setStock(newStock);
        productRepository.save(product);

        System.out.println("Updated stock for product " + product.getId() + ". New stock: " + newStock);
    }
}
