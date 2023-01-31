package com.artemgggi.webshop.dto;

import com.artemgggi.webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
//    @Query(value = "select name from product where name like %:keyword%",nativeQuery = true)
//    List<Product> findByNameContaining(@Param("keyword") String name);
}
