package com.artemgggi.webshop.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

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

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<CartItem> items;

    private String tokenSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        Double sum = 0.0;
        for (CartItem item : this.items) {
           sum = sum + item.getProduct().getPrice();
        }
        return sum;
    }

    public int getItemsNumber() {
        return this.items.size();
    }

    public Collection<CartItem> getItems() {
        return items;
    }

    public void setItems(Collection<CartItem> items) {
        this.items = items;
    }

    public String getTokenSession() {
        return tokenSession;
    }

    public void setTokenSession(String tokenSession) {
        this.tokenSession = tokenSession;
    }
}
