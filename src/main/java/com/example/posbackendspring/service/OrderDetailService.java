package com.example.posbackendspring.service;

import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.dto.impl.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetail(OrderDetailDTO orderDetailDTO);
    void updateOrderDetail(String itemId,OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(String orderDetailId);
    ItemStatus getOrderDetail(String orderDetailId);
    List<ItemDTO> getAllOrderDetail();
}
