package org.example.productcatalogservice.controllers;

import jakarta.websocket.server.PathParam;
import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService service;
    
    @GetMapping("/products")
    public List<Product> getAllProducts(){
        Product product = new Product();
        product.setTitle("Oppo Reno 15 pro mini");
        product.setId(1L);
        product.setDescription("Compact phone");
        List<Product> products  = new ArrayList<Product>();
        products.add(product);
        return products;
    }

    @GetMapping("products/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        ProductDto dto = new ProductDto();
        Product product = service.getProductById(id);
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        CategoryDto catDto = new CategoryDto();
        catDto.setId(product.getCategory().getId());
        catDto.setTitle(product.getCategory().getName());
        dto.setCatagoryDto(catDto);
        return dto;
    }

    @PostMapping("products")
    public ProductDto createProduct(@RequestBody ProductDto input) {
        // save product
        return input;
    }
}
