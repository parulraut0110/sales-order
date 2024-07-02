package com.parul.sales_order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parul.sales_order.entity.Orders;
import com.parul.sales_order.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Transactional
    public String getItemName(int orderId) {
        return orderRepo.getItemName(orderId);
    }
}
