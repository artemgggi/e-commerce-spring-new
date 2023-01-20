package com.artemgggi.webshop.model;

import jakarta.persistence.*;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int discount;
    @Transient
    private boolean isDiscount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isDiscount() {
        return this.discount != 0;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }
}
