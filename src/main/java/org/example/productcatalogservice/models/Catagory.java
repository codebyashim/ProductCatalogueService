package org.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Catagory extends BaseModel {
    private  String title;
    private  String description;
    private List<Product> products;
}