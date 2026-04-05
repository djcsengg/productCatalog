package org.example.projectcatalog.services;

import org.example.projectcatalog.models.Product;
import org.example.projectcatalog.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("storageProductService")
public class storageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Product getProductById(Long id) {
        Product product = (Product)redisTemplate.opsForHash().get("products",id);
       if(product==null) {
           Optional<Product> optionalProduct = productRepo.findById(id);
           if (optionalProduct.isPresent()) {
               redisTemplate.opsForHash().put("product", id, optionalProduct.get());
               return optionalProduct.get();
           }
           return null;
       }
       return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = productRepo.findById(product.getId());
        if(optionalProduct.isEmpty())
        {
            return productRepo.save(product);
        }
        else{
            return optionalProduct.get();
        }
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty())
        {
            return null;
        }
        productRepo.deleteById(id);
        return optionalProduct.get();
    }
}
