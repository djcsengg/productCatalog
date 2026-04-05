package org.example.projectcatalog.services;

import org.example.projectcatalog.models.Product;
import org.example.projectcatalog.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPASearchService implements ISearchService {

    @Autowired // This tells Spring to find the ProductRepo bean and "inject" it here
    private ProductRepo productRepo;

    @Override
    public Page<Product> searchProducts(String query, Integer pageNumber, Integer pageSize) {
        // Now productRepo is no longer null!
        Sort sort = Sort.by("productPrice").descending().and(Sort.by("id"));
        Page <Product> products = productRepo.findByNameEquals(query,PageRequest.of(pageNumber,pageSize,sort));
        return products;
    }
}
