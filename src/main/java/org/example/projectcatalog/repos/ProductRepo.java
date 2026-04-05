package org.example.projectcatalog.repos;

import org.example.projectcatalog.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    Page<Product> findByNameEquals(String name, Pageable pageable);
}
