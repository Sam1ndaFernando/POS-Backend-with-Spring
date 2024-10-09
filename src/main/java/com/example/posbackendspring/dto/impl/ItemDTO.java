package com.example.posbackendspring.dto.impl;

import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO implements SuperDTO, ItemStatus {
    private String itemCode;
    private String itemName;
    private int QTYOnHand;
    private double unitPrice;
    private List<OrderDetailDTO> orderList;
}
