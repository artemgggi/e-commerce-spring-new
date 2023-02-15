package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.ShoppingCart;
import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import com.artemgggi.webshop.service.WishListService;
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

    private final WishListService wishListService;

    private final ProductService productService;

    private final ShoppingCartService shoppingCartService;

    public CartController(WishListService wishListService,
                          ProductService productService,
                          ShoppingCartService shoppingCartService) {
        this.wishListService = wishListService;
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    public String getSessionToken(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("sessionToken");
    }

    @PostMapping("/addToCart")
    public String addToCart(HttpServletRequest request, @RequestParam("id") Long id,
                            @RequestParam("quantity") int quantity) {
        String sessionToken = getSessionToken(request);
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
        String sessionToken = getSessionToken(request);
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        model.addAttribute("categories", productService.getAllCategories());
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
        shoppingCartService.removeCartIemFromShoppingCart(id, sessionToken);
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
