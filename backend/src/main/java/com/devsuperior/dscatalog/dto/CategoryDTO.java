package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo requerido")
    private String name;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
