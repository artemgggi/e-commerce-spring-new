package com.artemgggi.webshop.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String sessionToken;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
    private Set<WishListItem> items = new HashSet<>();

    public WishList() {}

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

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Set<WishListItem> getItems() {
        return items;
    }

    public void setItems(Set<WishListItem> items) {
        this.items = items;
    }

    public int getItemsNumber() {
        return this.items.size();
    }
}