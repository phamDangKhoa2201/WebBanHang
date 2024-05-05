package com.project.shopaap.respones;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopaap.models.Order;
import com.project.shopaap.models.OrderDetail;
import com.project.shopaap.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailRespone {
    private Long id;
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("number_of_product")
    private int numberOfProduct;
    @JsonProperty("total_money")
    private Float totalMoney;
    @JsonProperty("color")
    private String color;
    public static OrderDetailRespone fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailRespone.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProduct(orderDetail.getNumberOfProduct())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();

    }
}
