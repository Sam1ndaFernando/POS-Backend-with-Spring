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
            logger.info("Customer updated successfully", customerId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.error("Failed to update customer", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Unable to update customer record due to internal server issue", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId){
        try{
            if (!Regex.customerIdValidate(customerId).matches()) {
                logger.error("Wrong customer ID", customerId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.deleteCustomer(customerId);
            logger.info("Customer Id deleted successfully", customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistException e){
            logger.error("Customer ID not found", customerId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Unable to delete customer record due to internal server issue", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{customerId}")
    public CustomerStatus getSelectedCustomer(@PathVariable("customerId") String customerId){
        logger.error("Invalid Customer Id", customerId);
        if (!Regex.customerIdValidate(customerId).matches()){
            logger.error("Customer ID is Not valid!");
            return new ErrorStatus(1,"Customer ID is Not valid!");
        }
        CustomerStatus customerStatus = customerService.getCustomer(customerId);
        if (customerStatus instanceof ErrorStatus) {
            logger.warn("Customer Id not found", customerId);
        } else {
            logger.info("Customer Id retrieved successfully", customerId);
        }
        return customerStatus;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        try {
            List<CustomerDTO> customers = customerService.getAllCustomer();
            if (customers.isEmpty()) {
                logger.warn("No customers found.");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.info("Successfully retrieved all customers", customers.size());
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving customer list", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
