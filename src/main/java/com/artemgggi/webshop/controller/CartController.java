package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.ShoppingCart;
import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String addToCart(HttpServletRequest request, @RequestParam("id") Long id,
                            @RequestParam("quantity") int quantity) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
            shoppingCartService.addShoppingCartFirstTime(id, sessionToken, quantity);
        } else {
            shoppingCartService.addToExistShoppingCart(id, sessionToken, quantity);
        }
        return "redirect:/admin/index";
    }

    @GetMapping("/shoppingCart")
    public String showShoppingCartView(HttpServletRequest request, Model model) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        model.addAttribute("categories", productService.getAllCategories());
        return "/shoppingCart";
    }
}
