package com.artemgggi.webshop.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import java.util.Set;


@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Transient
    private int productsNumber;
    public Set<Product> getProducts() {
        return products;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public int getProductsNumber() {
        return this.products.size();
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private Set<Product> products;
    public Category() {}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
