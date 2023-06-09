package com.cdms.gateway.repository;

import com.cdms.gateway.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findByOrderId(String id);
    List<Order> findByRollNum(String rollNum);
}
