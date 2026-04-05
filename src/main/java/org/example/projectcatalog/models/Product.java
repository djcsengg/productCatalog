package org.example.projectcatalog.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity //Creates Table
public class Product extends baseModel implements Serializable {

    String name;
    String ProductDescription;
    Double productPrice;
    String ImageUrl;
    @ManyToOne(cascade = CascadeType.ALL) // If we add or remove a product without a category or
                                        // delete a product with respect to a category

    @JsonManagedReference
    Category category;
    Boolean isPrime;

    public Product(){

        this.setCreatedAt(new Date());
        this.setLastUpdatedAt(new Date());
        this.setState(State.ACTIVE);
    }
}
