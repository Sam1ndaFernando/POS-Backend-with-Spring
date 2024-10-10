package com.example.posbackendspring.repository;

import com.example.posbackendspring.entity.impl.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailsEntity,String> {
}
