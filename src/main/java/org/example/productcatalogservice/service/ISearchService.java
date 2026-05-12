package org.example.productcatalogservice.service;

import org.example.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

public interface ISearchService {
    public Page<Product> searchProducts(String query, int pageSize, int pageNumber);
}
