package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CartController {
    @Autowired
    ProductService productService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/addToCart")
    public String addToCart(HttpSession session, Model model, @RequestParam("id") Long id,
                            @RequestParam("quantity") int quantity) {
        String sessionToken = (String) session.getAttribute("sessionToken");
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            session.setAttribute("sessionToken", sessionToken);
            shoppingCartService.addShopingCartFirstTime(id, sessionToken, quantity);
        } else {
            shoppingCartService.addToExistShoppingCart(id, sessionToken, quantity);
        }
        return "redirect:/";
    }
}
