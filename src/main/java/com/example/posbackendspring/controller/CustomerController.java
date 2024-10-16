package com.example.posbackendspring.controller;

import com.example.posbackendspring.customStatusCode.ErrorStatus;
import com.example.posbackendspring.dto.CustomerStatus;
import com.example.posbackendspring.dto.impl.CustomerDTO;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.service.CustomerService;
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
@RequestMapping("api/v1/customers")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO){
        customerDTO.setCustomerId(customerDTO.getCustomerId());
        try{
            customerService.saveCustomer(customerDTO);
            logger.info("Customer saved successfully", customerDTO.getCustomerId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.error("Failed to save customer", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Unable to save customer record due to internal server issue", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{customerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") String customerId,@RequestBody CustomerDTO customerDTO){
        try{
            customerService.updateCustomer(customerId,customerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId){
        try{
            if (!Regex.customerIdValidate(customerId).matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{customerId}")
    public CustomerStatus getSelectedCustomer(@PathVariable("customerId") String customerId){
        if (!Regex.customerIdValidate(customerId).matches()){
            return new ErrorStatus(1,"Customer ID is Not valid!");
        }
        return customerService.getCustomer(customerId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomer();
    }

}
