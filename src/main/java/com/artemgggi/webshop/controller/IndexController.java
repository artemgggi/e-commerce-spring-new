package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "/index";
    }
}
