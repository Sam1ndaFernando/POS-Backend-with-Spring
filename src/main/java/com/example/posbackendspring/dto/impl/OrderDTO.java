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
    private String customerId;
    private String name;
    private String city;
    private String tel;
    private List<OrderDTO> orderList;
}
