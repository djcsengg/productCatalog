package org.example.projectcatalog.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Category extends baseModel {
    String Name;
    String Description;
    List<Product> products;
}
