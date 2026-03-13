package org.example.projectcatalog.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class baseModel {
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;

    State state;
}
