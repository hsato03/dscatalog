package com.devsuperior.dscatalog.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1;

    private Long id;
    private String name;

    public Category() {}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
