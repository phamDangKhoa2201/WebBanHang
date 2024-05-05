package com.project.shopaap.respones;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopaap.models.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

 //toString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class ProductRespone extends BaseRespone{
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
    public static ProductRespone fromProduct(Product product){
        ProductRespone productRespone = ProductRespone.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productRespone.setCreatedAt(product.getCreatedAt());
        productRespone.setUpdatedAt(product.getUpdatedAt());
        return productRespone;
    }
}
