package org.example.projectcatalog.controllers;

import org.example.projectcatalog.dtos.SearchRequestDto;
import org.example.projectcatalog.models.Product;
import org.example.projectcatalog.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping("/search")
    public Page<Product> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
        // Provide defaults so .intValue() doesn't fail
        int page = (searchRequestDto.getPageNumber() == null) ? 0 : searchRequestDto.getPageNumber();
        int size = (searchRequestDto.getPageSize() == null) ? 10 : searchRequestDto.getPageSize();

        return searchService.searchProducts(searchRequestDto.getQuery(), page, size,searchRequestDto.getSortParams());
    }
}
