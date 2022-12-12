package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/example.html")
    public String showExample(Model model) {

        Product p = new Product();
        Long id = (long) 1;
        p = (Product) productRepository.findById(id).get();
        model.addAttribute("product", p);
        return "/example.html";
    }
}
