package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.model.ShoppingCart;
import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DetailsController {

    private final ProductService productService;

    private final ShoppingCartService shoppingCartService;

    public DetailsController(ProductService productService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/detail/{id}")
    public String getProductDetail(HttpServletRequest request, @PathVariable("id") Long id, Model model) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        Product p = productService.getProductById(id);
        model.addAttribute("product", p);
        model.addAttribute("categories", productService.getAllCategories());
        return "/detail";
    }
}
