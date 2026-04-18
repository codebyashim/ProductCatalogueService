package org.example.productcatalogservice.exceptions;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String message){
        super(message);
    }
}
