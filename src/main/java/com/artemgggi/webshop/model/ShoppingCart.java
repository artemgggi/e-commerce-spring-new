package com.artemgggi.webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private Double totalPrice;

    @Transient
    private int itemsNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();

    private String sessionToken;

    public Double getTotalPrice() {
        double sum = 0.0;
        for (CartItem item : this.items) {
           sum = sum + item.getProduct().getPrice() * item.getQuantity();
        }
        return sum;
    }
}
