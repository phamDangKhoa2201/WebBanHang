package com.project.shopaap.controllers;

import com.project.shopaap.components.LocalizationUtil;
import com.project.shopaap.dtos.OrderDetailDTO;
import com.project.shopaap.exceptions.DataNotFoundException;
import com.project.shopaap.models.OrderDetail;
import com.project.shopaap.respones.OrderDetailRespone;
import com.project.shopaap.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;
    private final LocalizationUtil localizationUtil;
    @PostMapping("")
    public ResponseEntity<?> createDetailOrder(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
            ){
        try {
            OrderDetail orderDetail = orderDetailService.createOrder(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailRespone.fromOrderDetail(orderDetail));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id
    ) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        return ResponseEntity.ok(OrderDetailRespone.fromOrderDetail(orderDetail));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ){
        List<OrderDetail> orderDetails= orderDetailService.findByOrderId(orderId);
        List<OrderDetailRespone> orderDetailRespones = orderDetails
                .stream()
                .map(OrderDetailRespone::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(orderDetailRespones);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ){
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id,orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        orderDetailService.deleteById(id);
        return ResponseEntity.ok("Delete orderdetail with id = "+ id +" successfully");
    }
}
