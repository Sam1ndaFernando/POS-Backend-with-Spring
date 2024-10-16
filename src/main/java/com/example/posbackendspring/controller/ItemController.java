package com.example.posbackendspring.controller;

import com.example.posbackendspring.customStatusCode.ErrorStatus;
import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.service.ItemService;
import com.example.posbackendspring.util.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemService itemService;
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO){
        itemDTO.setItemCode(itemDTO.getItemCode());
        try{
            itemService.saveItem(itemDTO);
            logger.info("Item created successfully ", itemDTO.getItemCode());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.warn("Failed to create item: ", itemDTO.getItemCode());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Internal server error while creating item: ", itemDTO.getItemCode(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{itemCode}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@PathVariable("itemCode") String itemCode,@RequestBody ItemDTO itemDTO){
        try{
            itemService.updateItem(itemCode,itemDTO);
            logger.info("Item updated successfully.", itemCode);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.warn("Failed to update item : ", itemCode);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Internal server error while updating item : ", itemCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "/{itemCode}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemCode") String itemCode){
        try{
            if (!Regex.itemCodeValidate(itemCode).matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            itemService.deleteItem(itemCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{itemCode}")
    public ItemStatus getSelectedItem(@PathVariable("itemCode") String itemCode){
        if (!Regex.itemCodeValidate(itemCode).matches()){
            return new ErrorStatus(1,"Item Code is Not valid!!!!!");
        }
        return itemService.getItem(itemCode);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItem(){
        return itemService.getAllItem();
    }
}
