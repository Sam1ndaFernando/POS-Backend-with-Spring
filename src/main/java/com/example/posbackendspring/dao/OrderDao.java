package com.example.posbackendspring.dao;

import com.example.posbackendspring.entity.impl.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity,String> {
}
