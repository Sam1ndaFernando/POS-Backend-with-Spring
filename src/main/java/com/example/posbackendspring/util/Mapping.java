package com.example.posbackendspring.util;

import com.example.posbackendspring.dto.impl.CustomerDTO;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.dto.impl.OrderDTO;
import com.example.posbackendspring.dto.impl.OrderDetailDTO;
import com.example.posbackendspring.entity.impl.CustomerEntity;
import com.example.posbackendspring.entity.impl.ItemEntity;
import com.example.posbackendspring.entity.impl.OrderDetailsEntity;
import com.example.posbackendspring.entity.impl.OrderEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper mapper;

    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        return mapper.map(customerDTO,CustomerEntity.class);
    }

    public CustomerDTO toCustomerDTO(CustomerEntity userEntity){
        return mapper.map(userEntity,CustomerDTO.class);
    }

    public List<CustomerDTO> customerList(List<CustomerEntity> customerList) {
        return mapper.map(customerList,new TypeToken<List<CustomerDTO>>(){}.getType());
    }

    public ItemEntity toItemEntity(ItemDTO itemDTO) {
        return mapper.map(itemDTO,ItemEntity.class);
    }

    public ItemDTO toItemDTO(ItemEntity itemEntity) {
        return mapper.map(itemEntity,ItemDTO.class);
    }

    public List<ItemDTO> toItemList(List<ItemEntity> itemList) {
        return mapper.map(itemList,new TypeToken<List<ItemDTO>>(){}.getType());
    }
    public OrderEntity toOrderEntity(OrderDTO orderDTO) {
        return mapper.map(orderDTO, OrderEntity.class);
    }
    public OrderDTO toOrderDTO(OrderEntity orderEntity){
        return mapper.map(orderEntity,OrderDTO.class);
    }
    public OrderDetailsEntity toOrderDetailEntity(OrderDetailDTO orderDetailDTO) {
        return mapper.map(orderDetailDTO, OrderDetailsEntity.class);
    }
}
