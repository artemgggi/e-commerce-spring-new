package com.artemgggi.webshop.model;

import jakarta.persistence.*;

@Entity
public class Account {

    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ALL = "ALL";

    @Id
    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    @Column(name = "encrypted_password", length = 128, nullable = false)
    private String encryptedPassword;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "user_role", length = 20, nullable = false)
    private String userRole;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", active=" + active +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}

