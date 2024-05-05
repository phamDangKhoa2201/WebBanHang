package com.project.shopaap.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "OrderId must be >0")
    private Long orderId;
    @JsonProperty("product_id")
    @Min(value = 1, message = "ProductId must be >0")
    private Long productId;
    @Min(value = 0, message = "Price must be >0")
    private Float price;
    @Min(value = 1, message = "Number of product must be >0")
    @JsonProperty("number_of_products")
    private int numberOfProducts;
    @Min(value = 0, message = "Number of product must be >0")
    @JsonProperty("total_money")
    private Float totalMoney;
    private String color;

}
