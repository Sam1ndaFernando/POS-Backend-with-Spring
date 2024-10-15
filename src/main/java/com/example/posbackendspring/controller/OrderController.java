package com.example.posbackendspring.controller;

import com.example.posbackendspring.customStatusCode.ErrorStatus;
import com.example.posbackendspring.dto.OrderStatus;
import com.example.posbackendspring.dto.impl.OrderDTO;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.service.OrderService;
import com.example.posbackendspring.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrder(@RequestBody OrderDTO orderDTO){
        orderDTO.setOrderID(orderDTO.getOrderID());
        try{
            orderService.saveOrder(orderDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{orderId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrder(@PathVariable("orderId") String orderId,@RequestBody OrderDTO orderDTO){
        try{
            orderService.updateOrder(orderId,orderDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") String orderId){
        try{
            if (!Regex.orderIdValidate(orderId).matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{orderId}")
    public OrderStatus getOrder(@PathVariable("orderId") String orderId){
        if (!Regex.orderIdValidate(orderId).matches()){
            return (OrderStatus) new ErrorStatus(1,"OrderId is Not valid!");
        }
        return (OrderStatus) orderService.getOrder(orderId);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrder();
    }
}
