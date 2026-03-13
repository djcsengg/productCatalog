package org.example.projectcatalog.services;

import org.example.projectcatalog.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public Product getProductById(Long id)
    {
        RestTemplate restTemplate = restTemplateBuilder.build();

    }
}
