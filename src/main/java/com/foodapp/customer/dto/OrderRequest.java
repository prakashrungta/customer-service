package com.foodapp.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private String itemName;
    private int quantity;
}