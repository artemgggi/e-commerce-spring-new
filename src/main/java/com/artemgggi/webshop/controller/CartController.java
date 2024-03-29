package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.cart.ShoppingCart;
import com.artemgggi.webshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CartController {

    private final ShoppingCartService shoppingCartService;

    public CartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public String getSessionToken(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("sessionToken");
    }

    @PostMapping("/addToCart")
    public String addToCart(HttpServletRequest request, @RequestParam("id") Long id) {
        String sessionToken = getSessionToken(request);
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
            int quantity = 1;
            shoppingCartService.addShoppingCartFirstTime(id, sessionToken, quantity);
        } else {
            int quantity = 1;
            shoppingCartService.addToExistShoppingCart(id, sessionToken, quantity);
        }
        return "redirect:/";
    }

    @GetMapping("/shoppingCart")
    public String showShoppingCartView(HttpServletRequest request, Model model) {
        String sessionToken = getSessionToken(request);
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "/shoppingCart";
    }

    @PostMapping("/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Long id,
                                 @RequestParam("quantity") int quantity) {
        shoppingCartService.updateShoppingCartItem(id, quantity);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/removeCartItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        return "redirect:/shoppingCart";
    }

    @GetMapping("/clearShoppingCart")
    public String clearShoopiString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        shoppingCartService.clearShoppingCart(sessionToken);
        return "redirect:/shoppingCart";
    }
}
