package org.example.projectcatalog.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDto {

    Long id;
    String title;
    Double price;
    String description;
    String category;
    String image;
}
