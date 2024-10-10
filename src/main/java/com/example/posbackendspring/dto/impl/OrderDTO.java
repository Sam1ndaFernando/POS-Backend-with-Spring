package com.example.posbackendspring.dto.impl;

import com.example.posbackendspring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO implements SuperDTO {
    private String orderID;
    private String date;
    private double discountRate;
    private double discount;
    private double subTotal;
    private double balance;
    private CustomerDTO customerId;
    private List<OrderDetailDTO> orderDetailDTO;
}
