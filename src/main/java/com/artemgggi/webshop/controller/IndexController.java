package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.dto.ShoppingCartRepository;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.model.ShoppingCart;
import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartService shoppingCartService;

    public IndexController(ProductRepository productRepository,
                           ProductService productService,
                           ShoppingCartRepository shoppingCartRepository,
                           ShoppingCartService shoppingCartService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartService = shoppingCartService;
    }

    public String getSessionToken(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("sessionToken");
    }

    @GetMapping("/")
    public String showIndex(HttpServletRequest request, Model model) {
        String sessionToken = getSessionToken(request);
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/index";
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("value") String value) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fragment/searchFragment");
        List<Product> products = productService.searchProductByNameLike(value);
        mv.addObject("products", products);
        return mv;
    }

    @GetMapping("/category/{id}")
    public String getProductByCategory(@PathVariable ("id") String id, HttpServletRequest request, Model model) {
        String sessionToken = getSessionToken(request);
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        List<Product> products = productService.searchProductByCategory(id);
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/categories";
    }
}
