package com.example.posbackendspring.service.impl;

import com.example.posbackendspring.controller.ItemController;
import com.example.posbackendspring.customStatusCode.ErrorStatus;
import com.example.posbackendspring.dao.ItemDao;
import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.entity.impl.ItemEntity;
import com.example.posbackendspring.exception.CustomerNotFoundException;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.dao.ItemDao;
import com.example.posbackendspring.service.ItemService;
import com.example.posbackendspring.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Autowired
    private Mapping mapping;
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Override
    public void saveItem(ItemDTO itemDTO) {
        logger.info("Save item with code: ", itemDTO.getItemCode());
        ItemEntity item = itemDao.save(mapping.toItemEntity(itemDTO));
        if (item==null){
            logger.error("Item could not be saved", itemDTO.getItemCode());
            throw new DataPersistException("Customer Note Saved");
        }else {
            logger.info("Item has been saved successfully", itemDTO.getItemCode());
        }
    }

    @Override
    public void updateItem(String itemId, ItemDTO itemDTO) {
        Optional<ItemEntity> tmpItem = itemDao.findById(itemId);
        if (tmpItem.isPresent()){
            tmpItem.get().setItemName(itemDTO.getItemName());
            tmpItem.get().setQTYOnHand(itemDTO.getQTYOnHand());
            tmpItem.get().setUnitPrice(itemDTO.getUnitPrice());
        }
    }

    @Override
    public void deleteItem(String itemId) {
        Optional<ItemEntity> tmpItem = itemDao.findById(itemId);
        if (!tmpItem.isPresent()){
            throw new CustomerNotFoundException("Item code with " + itemId + "Not Found!");
        }else {
            itemDao.deleteById(itemId);
        }
    }

    @Override
    public ItemStatus getItem(String itemCode) {
        if (itemDao.existsById(itemCode)){
            return mapping.toItemDTO(itemDao.getReferenceById(itemCode));
        }else {
            return new ErrorStatus(2,"Selected item not found");
        }
    }

    @Override
    public List<ItemDTO> getAllItem() {
        return mapping.toItemList(itemDao.findAll());
    }
}
