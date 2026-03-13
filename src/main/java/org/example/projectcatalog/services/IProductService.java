package org.example.projectcatalog.services;

import org.example.projectcatalog.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product replaceProduct(Long id, Product product);
    Product deleteProduct(Long id);
}
