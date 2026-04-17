package org.example.productcatalogservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends  BaseModel {
    private String title;
    private String imageUrl;
    private String description;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    // private String sellerName;
    private boolean isSaleSpecific; // special field
}

/*

Product    Category
   1    ->    1
   M    <-    1

*/