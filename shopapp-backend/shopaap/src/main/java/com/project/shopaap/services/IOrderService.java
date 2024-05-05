package com.project.shopaap.services;

import com.project.shopaap.dtos.OrderDTO;
import com.project.shopaap.exceptions.DataNotFoundException;
import com.project.shopaap.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    Order getOrderById(Long id);
    List<Order> findByUserId(Long userId);
    Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id);
}
