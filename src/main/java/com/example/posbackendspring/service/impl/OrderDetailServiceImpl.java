package com.example.posbackendspring.service.impl;

import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.dto.impl.OrderDetailDTO;
import com.example.posbackendspring.service.OrderDetailService;

import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
    public void saveOrderDetail(OrderDetailDTO orderDetailDTO) {

    }

    @Override
    public void updateOrderDetail(String itemId, OrderDetailDTO orderDetailDTO) {

    }

    @Override
    public void deleteOrderDetail(String orderDetailId) {

    }

    @Override
    public ItemStatus getOrderDetail(String orderDetailId) {
        return null;
    }

    @Override
    public List<ItemDTO> getAllOrderDetail() {
        return null;
    }
}
