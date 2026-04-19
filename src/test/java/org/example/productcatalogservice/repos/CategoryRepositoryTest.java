package org.example.productcatalogservice.repos;

import jakarta.transaction.Transactional;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repo;

    @Test
    @Transactional
    public void testFetchType(){
        Optional<Category> optionalCategory = repo.findById(1L);
        Category cat = optionalCategory.get();
        System.out.println(cat.getTitle());
        for (Product p : cat.getProducts()){
            System.out.println(p.getTitle());
        }
    }

    @Test
    @Transactional
    public void testNPlusOneProblem() {
        List<Category> categoryList = repo.findAll();
        for(Category category : categoryList) {
            System.out.println(category.getTitle());
            for(Product product : category.getProducts()) {
                System.out.println(product.getTitle());
            }
        }
    }
}