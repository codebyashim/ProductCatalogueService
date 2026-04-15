package org.example.productcatalogservice.service;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder builder;

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = builder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class,
                        id);
        if (validateFakeStoreResponse(fakeStoreProductDtoEntity)){
            return from(fakeStoreProductDtoEntity.getBody());
        }
        return null;
    }

    @Override
    public List<Product> getAllProductById() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = from(product);
        RestTemplate template = builder.build();
        ResponseEntity<FakeStoreProductDto> dto = template.postForEntity("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class);

        if (dto.hasBody() && dto.getStatusCode().equals(HttpStatusCode.valueOf(201))){
            return from(dto.getBody());
        }
        return null;
    }

    @Override
    public Product replaceProduct(Product input, Long id) {
        FakeStoreProductDto fakeStoreProductDto = from(input);
        ResponseEntity<FakeStoreProductDto> dto = putForEntity("https://fakestoreapi.com/products/{id}", fakeStoreProductDto, FakeStoreProductDto.class, id);
        if (validateFakeStoreResponse(dto)){
            return from(dto.getBody());
        }
        return null;
    }

    private  <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request,
                                               Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate template = builder.build();
        RequestCallback requestCallback = template.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = template.responseEntityExtractor(responseType);
        return template.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    private boolean validateFakeStoreResponse(ResponseEntity<FakeStoreProductDto> dto) {
        return dto.hasBody() && dto.getStatusCode().equals(HttpStatusCode.valueOf(200));
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return  product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getTitle());
        }
        return fakeStoreProductDto;
    }
}
