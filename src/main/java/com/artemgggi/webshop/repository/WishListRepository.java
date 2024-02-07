package com.artemgggi.webshop.repository;

import com.artemgggi.webshop.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findBySessionToken(String sessionTokenWishList);
}
