package com.artemgggi.webshop.repository;

import com.artemgggi.webshop.model.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem, Long> { }
