package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController controller;

    @MockBean
    private IProductService service;

    @Test
    public void TestGetProductById_WithValidProductId_ReturnsProductSuccessfully(){

        // Arrange
        Long id = 2L;
        String title  = "Vivo x200 Fe";
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);

        // Act
        when(service.getProductById(id)).thenReturn(product);

        ResponseEntity<ProductDto> response =  controller.getProductById(id);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals(title, response.getBody().getTitle());
    }

    @Test
    public void TestGetProductById_WithNegativeProductId_ResultsInIllegalArgumentException(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> controller.getProductById(-1L));

        assertEquals("INVALID INPUT",exception.getMessage());

    }

    @Test
    public void TestGetProductById_ThrowsRuntimeException_WhenProductServiceThrowsRuntimeException() {
        //Arrange
        Long id = 5L;
        when(service.getProductById(id)).thenThrow(new RuntimeException());

        //Act and Assert
        assertThrows(RuntimeException.class,
                () -> controller.getProductById(id));
    }
}