package org.example.productcatalogservice.repos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productcatalogservice.controllers.ProductController;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void TestGetAllProducts_Run_Successfully() throws Exception {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Oppo Reno 15 Pro mini");
        Product p2 = new Product();
        p2.setId(2L);
        p2.setTitle("Onepluse 15s");

        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);

        when(service.getAllProduct()).thenReturn(productList);

        ProductDto pdto1 = new ProductDto();
        pdto1.setId(p1.getId());
        pdto1.setTitle(p1.getTitle());
        ProductDto pdto2 = new ProductDto();
        pdto2.setId(p2.getId());
        pdto2.setTitle(p2.getTitle());

        List<ProductDto> productdtoList = new ArrayList<>();
        productdtoList.add(pdto1);
        productdtoList.add(pdto2);

        String expectedString = mapper.writeValueAsString(productdtoList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));
    }
}
