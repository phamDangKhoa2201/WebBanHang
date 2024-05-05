package com.project.shopaap.services;

import com.project.shopaap.dtos.ProductDTO;
import com.project.shopaap.dtos.ProductImageDTO;
import com.project.shopaap.exceptions.DataNotFoundException;
import com.project.shopaap.models.Product;
import com.project.shopaap.models.ProductImage;
import com.project.shopaap.respones.ProductRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
    public Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long productId) throws Exception;
    Page<ProductRespone> getAllProducts(String keyword,
                                        Long Category_id,PageRequest pageRequest);
    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long id);
    boolean existsByName(String name);
    public ProductImage createProductImage(long productId,
                                           ProductImageDTO productImageDTO) throws Exception;

}
