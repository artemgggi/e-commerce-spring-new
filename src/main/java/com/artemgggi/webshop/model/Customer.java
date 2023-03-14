package com.artemgggi.webshop.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "customers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "image", "phone_number"}))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String country;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;

    private String password;
    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "city")
    private String city;

//    @OneToOne(mappedBy = "customer")
//    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
