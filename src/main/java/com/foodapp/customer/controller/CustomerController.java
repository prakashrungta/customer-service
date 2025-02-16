package com.foodapp.customer.controller;

import com.foodapp.customer.dto.OrderRequest;
import com.foodapp.customer.entity.Customer;
import com.foodapp.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
//@Tag(name = "Customer Controller ", description = "APIs for managing Customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${welcome.msg}")
    private String welcomeMessage;

    public CustomerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/customers")
    @Tag(name = "Customer Controller", description = "APIs for fetching customer")
    public List<Customer> getCustomer() {

    System.out.println("Welcome Message is " + welcomeMessage);
    Logger logger = LoggerFactory.getLogger(CustomerController.class);
        logger.info("Fetching all customers");
        return customerService.getCustomer();
    }

    @PostMapping("/customers")
    @Tag(name = "Customer Controller", description = "APIs for registering customer")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }


    @PostMapping("/{customerId}/order")
    public ResponseEntity<String> placeOrder(
            @PathVariable String customerId,
            @RequestBody OrderRequest orderRequest) {

        System.out.println("orderServiceUrl " + orderServiceUrl);
        // Define Order Service URL (Use service name if running in Kubernetes)


        String finalOrderServiceUrl = orderServiceUrl.replace("{customerId}", String.valueOf(customerId));

        System.out.println("finalOrderServiceUrl" + orderServiceUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest, headers);

        // Call Order Service API
        ResponseEntity<String> response = restTemplate.postForEntity(finalOrderServiceUrl, orderRequest, String.class);

        return ResponseEntity.ok("Order placed via Customer Service: " + response.getBody());
    }

}
