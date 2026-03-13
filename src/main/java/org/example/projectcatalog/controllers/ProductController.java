package org.example.projectcatalog.controllers;

import org.example.projectcatalog.dtos.ProductDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    //Get One Product
    @GetMapping("/products/{id}")
    public ProductDto getProductDetails(@PathVariable Long id)
    {

        return null;
    }

    //Get All Product
    @GetMapping("/products")
    public ProductDto getAllProducts(@RequestBody ProductDto productDto)
    {
        return null;
    }

    //Add New Product API
    @PostMapping("/products")
    public ProductDto addProduct(@RequestBody ProductDto productDto)
    {
        return null;
    }

    //Update a product
    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id,@RequestBody ProductDto productDto)
    {
        return null;
    }

    //Delete a product
    @DeleteMapping("/products/{id}")
    public ProductDto deleteProduct(@PathVariable Long id)
    {
        return null;
    }
}
