package org.example.productcatalogservice.service;

import org.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    List<Product> getAllProduct();

    Product createProduct(Product product);

    Product replaceProduct(Product input, Long id);

    void deleteProduct(Long id);

    Product getProductDetailsBasedOnUserRoll(Long userId, Long productId);
}
