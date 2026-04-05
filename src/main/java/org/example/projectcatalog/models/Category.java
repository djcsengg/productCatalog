package org.example.projectcatalog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends baseModel implements Serializable {
    String Name;
    String Description;
    @OneToMany(mappedBy = "category") //Notifying JPA that this is already taken care using "category" in product class
    @JsonBackReference
    List<Product> products;
}
