package org.example.projectcatalog.services;

import org.example.projectcatalog.dtos.FakeStoreDto;
import org.example.projectcatalog.models.Category;
import org.example.projectcatalog.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id)
    {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto> fakeStoreDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreDto.class,id);

        if(fakeStoreDto.getBody()!=null && fakeStoreDto.getStatusCode().equals(HttpStatusCode.valueOf(200)))
        {
            return from(fakeStoreDto.getBody());
        }

        return null;
    }

    //Mapper
    private Product from(FakeStoreDto fakeStoreDto)
    {
        Product product = new Product();
        product.setId(fakeStoreDto.getId());
        product.setProductName(fakeStoreDto.getTitle());
        product.setProductDescription(fakeStoreDto.getDescription());
        product.setProductPrice(fakeStoreDto.getPrice());
        product.setImageUrl(fakeStoreDto.getImage());

        Category category = new Category();
        category.setName(fakeStoreDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
