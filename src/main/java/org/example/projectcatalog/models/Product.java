package org.example.projectcatalog.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends baseModel {

    String ProductName;
    String ProductDescription;
    Double ProductPrice;
}
