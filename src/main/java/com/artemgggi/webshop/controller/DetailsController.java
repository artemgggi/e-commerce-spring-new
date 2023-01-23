package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DetailsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model) {
        Product p = productService.getProductById(id);
        model.addAttribute("product", p);
        model.addAttribute("categories", productService.getAllCategories());
        return "/detail";
    }
}
