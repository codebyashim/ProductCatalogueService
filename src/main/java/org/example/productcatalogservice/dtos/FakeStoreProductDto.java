package org.example.productcatalogservice.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.productcatalogservice.models.Category;

//@Getter
//@Setter
@Data
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
