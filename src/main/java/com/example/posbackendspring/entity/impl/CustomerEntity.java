package com.example.posbackendspring.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String customerId   ;
    private String name;
    private String city;
    private String tel;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<OrderEntity> orderList;
}
