package org.example.projectcatalog.services;

import org.example.projectcatalog.dtos.SortParam;
import org.example.projectcatalog.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISearchService {

    Page<Product> searchProducts(String query, Integer pageNumber, Integer pageSize,List<SortParam> sortParams);

}
