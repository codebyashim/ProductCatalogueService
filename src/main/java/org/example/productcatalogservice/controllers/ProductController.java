package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    
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
}
