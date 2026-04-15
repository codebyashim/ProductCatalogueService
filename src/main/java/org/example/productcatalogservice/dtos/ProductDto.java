package org.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private  Long id;
    private  String title;
    private  String imageUrl;
    private  String description;
    private  Double price;
    private CategoryDto catagoryDto;
}
