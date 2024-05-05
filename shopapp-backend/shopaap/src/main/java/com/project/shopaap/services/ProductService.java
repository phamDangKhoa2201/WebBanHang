package com.project.shopaap.services;

import com.project.shopaap.dtos.ProductDTO;
import com.project.shopaap.dtos.ProductImageDTO;
import com.project.shopaap.exceptions.DataNotFoundException;
import com.project.shopaap.exceptions.InvalidParamException;
import com.project.shopaap.models.Category;
import com.project.shopaap.models.Product;
import com.project.shopaap.models.ProductImage;
import com.project.shopaap.repositories.CategoryRepository;
import com.project.shopaap.repositories.ProductImageRepository;
import com.project.shopaap.repositories.ProductRepository;
import com.project.shopaap.respones.ProductRespone;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Transactional
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category categoryExisting =  categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find category with" +
                        " id = "+productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(categoryExisting)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws DataNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(()-> new DataNotFoundException
                        ("Cannot find product with id = "+productId));
    }

    @Override
    public Page<ProductRespone> getAllProducts(String keyword,
                                               Long categoryId,
                                               PageRequest pageRequest) {
        //lấy sản phẩm theo page và limit
        Page<Product> productsPage;
        productsPage = productRepository.searchProduct(categoryId,keyword,pageRequest);
        return productsPage.map(ProductRespone::fromProduct);
    }
    @Transactional
    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            //copy thuộc tinh từ DTO -> product
            // có thé sử dụng Model Mapper
            Category categoryExisting =  categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find category with" +
                            " id = "+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(categoryExisting);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;

    }
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);

    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    @Transactional
    @Override
    public ProductImage createProductImage(long productId,
                                            ProductImageDTO productImageDTO) throws Exception
    {
        Product productExisting =  productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with" +
                        " id = "+productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(productExisting)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //k cho insert qua 5 anh 1 san pham
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of images must be <= "
                    +ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }

}
