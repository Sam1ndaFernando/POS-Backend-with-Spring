package com.example.posbackendspring.service.impl;

import com.example.posbackendspring.dao.OrderDao;
import com.example.posbackendspring.dto.OrderStatus;
import com.example.posbackendspring.dto.impl.OrderDTO;
import com.example.posbackendspring.dto.impl.OrderDetailDTO;
import com.example.posbackendspring.entity.impl.OrderEntity;
import com.example.posbackendspring.exception.CustomerNotFoundException;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.service.OrderDetailService;
import com.example.posbackendspring.service.OrderService;
import com.example.posbackendspring.util.AppUtil;
import com.example.posbackendspring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    public void saveOrder(OrderDTO orderDTO) {
        OrderEntity order = orderDao.save(mapping.toOrderEntity(orderDTO));
        if (order==null){
            throw new DataPersistException("Order Saved");
        }else {
            for (OrderDetailDTO orderDetailDTO:orderDTO.getOrderDetailDTO()){
                orderDetailDTO.setId(AppUtil.generateOrderDetailId());
                orderDetailDTO.setOrder(orderDTO);
                orderDetailService.saveOrderDetail(new OrderDetailDTO(
                        orderDetailDTO.getId(),
                        orderDetailDTO.getDate(),
                        orderDetailDTO.getCustomerId(),
                        orderDetailDTO.getCustomerName(),
                        orderDetailDTO.getCustomerCity(),
                        orderDetailDTO.getCustomerTel(),
                        orderDetailDTO.getItemName(),
                        orderDetailDTO.getOrderQTY(),
                        orderDetailDTO.getUnitPrice(),
                        orderDetailDTO.getItem(),
                        orderDetailDTO.getOrder()
                ));
            }
        }
    }

    @Override
    public void updateOrder(String orderId, OrderDTO orderDTO) {
        Optional<OrderEntity> tmpOrder = orderDao.findById(orderId);
        if (tmpOrder.isPresent()){
            tmpOrder.get().setDate(orderDTO.getDate());
            tmpOrder.get().setDiscountRate(orderDTO.getDiscountRate());
            tmpOrder.get().setDiscount(orderDTO.getDiscount());
            tmpOrder.get().setSubTotal(orderDTO.getSubTotal());
            tmpOrder.get().setBalance(orderDTO.getBalance());
            tmpOrder.get().setCustomer(mapping.toCustomerEntity(orderDTO.getCustomerId()));
            tmpOrder.get().setOrderDetailsList(mapping.toOrderEntityList(orderDTO.getOrderDetailDTO()));
        }
    }

    @Override
    public void deleteOrder(String orderId) {
        Optional<OrderEntity> tmpOrder = orderDao.findById(orderId);
        if (!tmpOrder.isPresent()){
            throw new CustomerNotFoundException("OrderId with " + orderId + "Not Found!");
        }else {
            orderDao.deleteById(orderId);
        }
    }

    @Override
    public OrderStatus getOrder(String orderId) {
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        return null;
    }
}
