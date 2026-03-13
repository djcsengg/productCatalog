package org.example.projectcatalog.controllers;

import org.example.projectcatalog.dtos.CategoryDto;
import org.example.projectcatalog.dtos.FakeStoreDto;
import org.example.projectcatalog.dtos.ProductDto;
import org.example.projectcatalog.models.Category;
import org.example.projectcatalog.models.Product;
import org.example.projectcatalog.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    //Get One Product
    @GetMapping("/products/{id}")
    public ProductDto getProductDetails(@PathVariable Long id)
    {
        Product product = productService.getProductById(id);
        if(product == null) return null;
        return from(product);
    }

    //Get All Product
    @GetMapping("/products")
    public List<ProductDto> getAllProducts()
    {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for(Product product : products)
        {
            productDtos.add(from(product));
        }

        return productDtos;
    }


    // Add New Product
    @PostMapping("/products")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        Product product = to(productDto);
        Product savedProduct = productService.createProduct(product);
        return from(savedProduct);
    }

    // Update/Replace a product
    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = to(productDto);
        Product updatedProduct = productService.replaceProduct(id, product);
        return from(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/products/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        Product deletedProduct = productService.deleteProduct(id);
        return from(deletedProduct);
    }

    // Mapper to convert DTO to Model
    private Product to(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductName(productDto.getName());
        product.setProductDescription(productDto.getDescription());
        product.setProductPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }



    //Mapperto convert Model to DTO
    private ProductDto from(Product product)
    {
        ProductDto productDto = new ProductDto();
        productDto.setId (product.getId());
        productDto.setName(product.getProductName());
        productDto.setDescription(product.getProductDescription());
        productDto.setPrice(product.getProductPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory()!=null)
        {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
