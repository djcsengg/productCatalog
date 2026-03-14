package org.example.projectcatalog.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass //Does not create table for this class but creates all these attributes in inherited classes
public class baseModel {
    @Id //Denoting primary Key
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;

    State state;
}
