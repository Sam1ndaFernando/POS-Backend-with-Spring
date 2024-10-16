package com.example.posbackendspring.service.impl;

import com.example.posbackendspring.dao.OrderDetailDao;
import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.dto.impl.OrderDetailDTO;
import com.example.posbackendspring.entity.impl.OrderDetailsEntity;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.service.OrderDetailService;
import com.example.posbackendspring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private Mapping mapping;
    Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Override
    public void saveOrderDetail(OrderDetailDTO orderDetailDTO) {
        logger.info("Save order detail Id", orderDetailDTO.getId());
        OrderDetailsEntity orderDetailsEntity = orderDetailDao.save(mapping.toOrderDetailEntity(orderDetailDTO));
        if (orderDetailsEntity==null){
            logger.error("Order detail could not be saved", orderDetailDTO.getId());
            throw new DataPersistException("Order detail not saved");
        } else {
            logger.info("Order detail has been saved successfully", orderDetailDTO.getId());
        }
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
