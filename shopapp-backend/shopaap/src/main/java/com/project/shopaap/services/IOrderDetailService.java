package com.project.shopaap.services;

import com.project.shopaap.dtos.OrderDTO;
import com.project.shopaap.dtos.OrderDetailDTO;
import com.project.shopaap.exceptions.DataNotFoundException;

import com.project.shopaap.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrder(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    OrderDetail getOrderDetailById(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
