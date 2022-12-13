package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/productsList")
    public String showProducts(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);

//        Product p = new Product();
//        Long id = (long) 1;
//        p = (Product) productRepository.findById(id).get();
//        model.addAttribute("product", p);
        return "/productsList";
    }

    @GetMapping("/")
    public String showAddProducts() {
        return "/addProduct";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@RequestParam("pname") String name,
                              @RequestParam("pprice") int price,
                              @RequestParam("pdescription") String description) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        productRepository.save(p);
        return "redirect:/productsList";
    }
}
