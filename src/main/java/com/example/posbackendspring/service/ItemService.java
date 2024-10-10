package com.example.posbackendspring.service;

import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);
    void updateItem(String itemId,ItemDTO itemDTO);
    void deleteItem(String itemId);
    ItemStatus getItem(String itemId);
    List<ItemDTO> getAllItem();
}
