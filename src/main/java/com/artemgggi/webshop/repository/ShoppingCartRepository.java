package com.artemgggi.webshop.repository;

import com.artemgggi.webshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findBySessionToken(String sessionToken);
}
