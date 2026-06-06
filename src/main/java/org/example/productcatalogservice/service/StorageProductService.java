package org.example.productcatalogservice.service;

import org.example.productcatalogservice.dtos.UserDto;
import org.example.productcatalogservice.exceptions.ProductAlreadyExistException;
import org.example.productcatalogservice.exceptions.ProductNotFoundException;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.models.State;
import org.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = repo.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public List<Product> getAllProduct() {
        return repo.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = repo.findById(product.getId());
        if (optionalProduct.isEmpty()) {
            return repo.save(product);
        } else {
          throw new ProductAlreadyExistException("Product already exist");
        }
    }

    @Override
    public Product replaceProduct(Product input, Long id) {
        input.setId(id);
        Optional<Product> optionalProduct = repo.findById(input.getId());
        if (optionalProduct.isPresent()) {
            input.setLastUpdatedAt(new Date());
            return repo.save(input);
        } else {
            throw new ProductNotFoundException("Product not found.");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        } else {
            Product p = optionalProduct.get();
            if (p.getState().equals(State.ACTIVE)){
                p.setState(State.INACTIVE);
                repo.save(p);
            } else {
                repo.deleteById(id);
            }
        }

    }

    @Override
    public Product getProductDetailsBasedOnUserRoll(Long userId, Long productId) {
        Product product = repo.findById(productId).orElse(null);
        if (product != null) {
            UserDto dto = restTemplate.getForEntity("http://ProjectCatelogueUserAuthService/users/{userId}", UserDto.class, userId).getBody();
            if (dto != null){
                System.out.println(dto.getEmail());
                return product;
            }
        }
        return null;
    }
}
