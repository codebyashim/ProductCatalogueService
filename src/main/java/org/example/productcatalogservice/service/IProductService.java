package org.example.productcatalogservice.service;

import org.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    List<Product> getAllProductById();

    Product createProduct(Product product);

    Product replaceProduct(Product input, Long id);
}
