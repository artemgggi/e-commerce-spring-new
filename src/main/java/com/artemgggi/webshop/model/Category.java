package com.artemgggi.webshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @Transient
    private int productsNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

    public Category() { }

    public Category(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getProductsNumber() {
        return this.products.size();
    }
}
