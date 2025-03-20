package com.microservice.order_service.feignclient;

import com.microservice.order_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceClient {
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable Long id);
}
