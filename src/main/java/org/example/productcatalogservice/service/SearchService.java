package org.example.productcatalogservice.service;

import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchService implements ISearchService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Page<Product> searchProducts(String query, int pageSize, int pageNumber) {
        Sort sortBy_Price_Desc = Sort.by("price").descending();
        Sort sortById_desc = Sort.by("id").descending();
        Sort finalSort = sortBy_Price_Desc.and(sortById_desc);
        return productRepo.findProductByTitle(query, PageRequest.of(pageNumber, pageSize, finalSort));
    }
}
