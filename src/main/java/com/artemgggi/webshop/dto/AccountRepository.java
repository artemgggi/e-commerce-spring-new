package com.artemgggi.webshop.dto;

import com.artemgggi.webshop.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
   Account findByUserName(String userName);
}

