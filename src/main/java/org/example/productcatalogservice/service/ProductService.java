package org.example.productcatalogservice.service;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder builder;

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = builder.build();
        FakeStoreProductDto dto =  restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(product.getImageUrl());
        Category category = new Category();
        category.setName(dto.getDescription());
        product.setCategory(category);

        return product;
    }

    @Override
    public List<Product> getAllProductById() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Product input, Long id) {
        return null;
    }
}
