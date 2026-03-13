package org.example.projectcatalog.services;

import org.example.projectcatalog.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    List<Product> getAllProducts();
}
