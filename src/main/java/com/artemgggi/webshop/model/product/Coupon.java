package com.artemgggi.webshop.model.product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int discount;
    @Transient
    private boolean isDiscount;

    public boolean isDiscount() {
        return this.discount != 0;
    }
}
