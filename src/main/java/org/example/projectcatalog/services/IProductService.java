package org.example.projectcatalog.services;

import org.example.projectcatalog.models.Product;

public interface IProductService {

    Product getProductById(Long id);
}
