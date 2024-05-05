package com.project.shopaap.controllers;

import com.project.shopaap.components.LocalizationUtil;
import com.project.shopaap.dtos.OrderDTO;
import com.project.shopaap.models.Order;
import com.project.shopaap.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final IOrderService orderService;
    private final LocalizationUtil localizationUtil;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
                                              BindingResult result){
        try {
            if(result.hasErrors()){

                List<String> errorMessage = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return  ResponseEntity.badRequest().body(errorMessage);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrderUser(@Valid @PathVariable("user_id") Long userId){
        try {
            List<Order> orders =orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId){
        try {
            Order existingOrder = orderService.getOrderById(orderId);
            return ResponseEntity.ok(existingOrder);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}") //Công việc của Admin
    public ResponseEntity<?> updateOrder(@Valid @PathVariable long id,
                                         @Valid @RequestBody OrderDTO orderDTO
    ){
        try {
            Order existingOrder = orderService.updateOrder(id,orderDTO);
            return ResponseEntity.ok(existingOrder);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}") //xoá mềm capaj nhật trường active = false
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Delete order successfully ");
    }
}
