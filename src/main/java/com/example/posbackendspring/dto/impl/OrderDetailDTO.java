package com.example.posbackendspring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO {
    private String id;
    private LocalDate date;
    private String customerId;
    private String customerName;
    private String customerCity;
    private String customerTel;
    private String itemCode;
    private String itemName;
    private int orderQTY;
    private double unitPrice;
}
