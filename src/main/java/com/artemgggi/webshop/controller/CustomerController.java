package com.artemgggi.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/shoppingCartCustomer")
    public String getShoppingCartCustomer() {
        return "/shoppingCartCustomer";
    }
}
