package org.example.projectcatalog.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity //Creates Table
public class Product extends baseModel {

    String ProductName;
    String ProductDescription;
    Double ProductPrice;
    String ImageUrl;
    @ManyToOne(cascade = CascadeType.ALL) // If we add or remove a product without a category or
                                         // delete a product with respect to a category
    Category category;
    Boolean isPrime;
}
