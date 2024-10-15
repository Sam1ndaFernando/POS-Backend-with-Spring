package com.example.posbackendspring.dao;

import com.example.posbackendspring.entity.impl.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetailsEntity,String> {
}
