package org.example.projectcatalog.services;

import org.example.projectcatalog.dtos.FakeStoreDto;
import org.example.projectcatalog.models.Category;
import org.example.projectcatalog.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreDto[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products/", FakeStoreDto[].class);

        List<Product> products = new ArrayList<Product>();
        for(FakeStoreDto fakeStoreDto : response.getBody())
        {
            Product product = from(fakeStoreDto);
            products.add(product);
        }
        return products;
    }

    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                  Class<T> responseType, Object... uriVariables)
    {
        RestTemplate restTemplate =  restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


    //Mapper
    public Product replaceProduct(Product product,Long id) {
        FakeStoreDto  input = from(product);
        FakeStoreDto output = requestForEntity("http://fakestoreapi.com/products/{id}",HttpMethod.PUT,input,
                        FakeStoreDto.class,id).getBody();
        return from(output);
    }

    private FakeStoreDto from(Product product) {
        // Add implementation here
        FakeStoreDto fakeStoreProductDto = new FakeStoreDto();
        fakeStoreProductDto.setImage(product.getImageUrl());
        return fakeStoreProductDto;
    }


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
