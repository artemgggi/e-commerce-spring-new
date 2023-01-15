package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/index";
    }

    @GetMapping("/productList")
    public String showProductList(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/productList";
    }
}
