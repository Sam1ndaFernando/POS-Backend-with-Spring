package com.example.posbackendspring.service;

import com.example.posbackendspring.dto.OrderStatus;
import com.example.posbackendspring.dto.impl.OrderDTO;

import java.util.List;

public interface OrderService {
    void saveOrder(OrderDTO orderDTO);
    void updateOrder(String orderId,OrderDTO orderDTO);
    void deleteOrder(String orderId);
    OrderStatus getOrder(String orderId);
    List<OrderDTO> getAllOrder();
}
