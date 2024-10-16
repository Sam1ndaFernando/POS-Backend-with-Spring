package com.example.posbackendspring.service.impl;

import com.example.posbackendspring.customStatusCode.ErrorStatus;
import com.example.posbackendspring.dao.CustomerDao;
import com.example.posbackendspring.dto.CustomerStatus;
import com.example.posbackendspring.dto.impl.CustomerDTO;
import com.example.posbackendspring.entity.impl.CustomerEntity;
import com.example.posbackendspring.exception.CustomerNotFoundException;
import com.example.posbackendspring.exception.DataPersistException;
import com.example.posbackendspring.dao.CustomerDao;
import com.example.posbackendspring.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Mapping mapping;
    static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        logger.info("save customer", customerDTO.getCustomerId());
        CustomerEntity customer = customerDao.save(mapping.toCustomerEntity(customerDTO));
        if (customer==null){
            logger.error("Customer Id is null", customerDTO.getCustomerId());
            throw new DataPersistException("Customer Note Saved");
        }else {
            logger.info("Customer with Id has been saved successfully", customerDTO.getCustomerId());
        }
    }

    @Override
    public void updateCustomer(String customerId, CustomerDTO customerDTO) {
        Optional<CustomerEntity> tmpCustomer = customerDao.findById(customerId);
        if (tmpCustomer.isPresent()){
            tmpCustomer.get().setName(customerDTO.getName());
            tmpCustomer.get().setCity(customerDTO.getCity());
            tmpCustomer.get().setTel(customerDTO.getTel());
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        Optional<CustomerEntity> tmpCustomer = customerDao.findById(customerId);
        if (!tmpCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer ID with " + customerId + "Not Found!");
        }else {
            customerDao.deleteById(customerId);
        }
    }

    @Override
    public CustomerStatus getCustomer(String customerId) {
        if (customerDao.existsById(customerId)){
            return mapping.toCustomerDTO(customerDao.getReferenceById(customerId));
        }else {
            return new ErrorStatus(2,"Selected Customer not found");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        return mapping.customerList(customerDao.findAll());
    }
}
