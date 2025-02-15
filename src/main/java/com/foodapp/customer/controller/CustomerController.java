package com.foodapp.customer.controller;

import com.foodapp.customer.entity.Customer;
import com.foodapp.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
//@Tag(name = "Customer Controller ", description = "APIs for managing Customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    @Tag(name = "Customer Controller", description = "APIs for fetching customer")
    public List<Customer> getCustomer() {
        return customerService.getCustomer();
    }

  @PostMapping("/customers")
  @Tag(name = "Customer Controller", description = "APIs for registering customer")
  public ResponseEntity<Customer> register(@RequestBody Customer customer) {
    return ResponseEntity.ok(customerService.saveCustomer(customer));
        }
}
