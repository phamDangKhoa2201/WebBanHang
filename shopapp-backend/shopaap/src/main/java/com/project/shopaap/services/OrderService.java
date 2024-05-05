package com.project.shopaap.services;

import com.project.shopaap.dtos.OrderDTO;
import com.project.shopaap.exceptions.DataNotFoundException;
import com.project.shopaap.models.Order;
import com.project.shopaap.models.OrderStatus;
import com.project.shopaap.models.User;
import com.project.shopaap.repositories.OrderRepository;
import com.project.shopaap.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Transactional
    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        //kiểm tra userId có tồn tại k
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+orderDTO.getUserId()));
        //convert orderDTO ->Order
        //su dụng ModelMapper
        //tạo 1 luồng bằng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO,order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null ?  LocalDate.now():orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);

        return order;

    }

    @Override
    public Order getOrderById(Long id) {

        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new DataNotFoundException("Cannot find order with id: "+orderId));
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(()->
                new DataNotFoundException("Cannot find user with id: "+orderId));
        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        modelMapper.map(orderDTO,order);
        order.setUser(existingUser);
        return orderRepository.save(order);

    }
    @Transactional
    @Override
    public void deleteOrder(Long id) {
        Order optionalOrder= orderRepository.findById(id)
                .orElse(null);
        //k xoá cứng -> hãy xoá mềm
        if(optionalOrder != null){
            optionalOrder.setActive(false);
            orderRepository.save(optionalOrder);
        }
    }
}
