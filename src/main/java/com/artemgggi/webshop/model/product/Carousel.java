package com.artemgggi.webshop.model.product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Carousel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "image")
    private String image;
}
