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
import com.example.posbackendspring.exception.ItemNotFoundException;
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
    public void updateItem(String itemCode, ItemDTO itemDTO) {
        logger.info("Update item with code: ", itemCode);
        Optional<ItemEntity> tmpItem = itemDao.findById(itemCode);
        if (tmpItem.isPresent()){
            tmpItem.get().setItemName(itemDTO.getItemName());
            tmpItem.get().setQTYOnHand(itemDTO.getQTYOnHand());
            tmpItem.get().setUnitPrice(itemDTO.getUnitPrice());
            logger.info("Item has been updated successfully", itemCode);
        } else {
            logger.warn("Item not found for update", itemCode);
        }
    }

    @Override
    public void deleteItem(String itemCode) {
        Optional<ItemEntity> tmpItem = itemDao.findById(itemCode);
        if (!tmpItem.isPresent()){
            logger.info("Delete item with code: ", itemCode);
            throw new ItemNotFoundException("Item code with " + itemCode + "Not Found!");
        }else {
            itemDao.deleteById(itemCode);
            logger.info("Item has been deleted successfully", itemCode);
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
