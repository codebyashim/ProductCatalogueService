package org.example.productcatalogservice.controllers;

import jakarta.websocket.server.PathParam;
import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService service;
    
    @GetMapping
    public List<Product> getAllProducts(){
        Product product = new Product();
        product.setTitle("Oppo Reno 15 pro mini");
        product.setId(1L);
        product.setDescription("Compact phone");
        List<Product> products  = new ArrayList<Product>();
        products.add(product);
        return products;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        if (productId <= 0){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw new IllegalArgumentException("Product id should be greater than 0");
        }

        Product product = service.getProductById(productId);
        if (product != null) {
            ProductDto dto = from(product);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto input) {
        Product product = from(input);
        product = service.createProduct(product);
        if (product != null) {
            ProductDto dto = from(product);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto input) {
        Product product = from(input);
        product = service.replaceProduct(product, id);
        if (product != null) {
            ProductDto dto = from(product);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setTitle(product.getTitle());
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setDescription(product.getCategory().getDescription());
            categoryDto.setTitle(product.getCategory().getTitle());
            categoryDto.setId(product.getCategory().getId());
            productDto.setCatagoryDto(categoryDto);
        }

        return productDto;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCatagoryDto() != null) {
            Category category = new Category();
            category.setTitle(productDto.getCatagoryDto().getTitle());
            category.setId(productDto.getCatagoryDto().getId());
            product.setCategory(category);
        }
        return product;
    }
}
