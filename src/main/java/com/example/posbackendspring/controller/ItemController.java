package com.example.posbackendspring.controller;

import com.example.posbackendspring.customStatusCode.ErrorStatus;
import com.example.posbackendspring.dto.ItemStatus;
import com.example.posbackendspring.dto.impl.ItemDTO;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.service.ItemService;
import com.example.posbackendspring.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/items")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO){
        itemDTO.setItemCode(itemDTO.getItemCode());
        try{
            itemService.saveItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{itemCode}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@PathVariable("itemCode") String itemCode,@RequestBody ItemDTO itemDTO){
        try{
            itemService.updateItem(itemCode,itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
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
}
