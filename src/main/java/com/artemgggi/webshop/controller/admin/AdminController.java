package com.artemgggi.webshop.controller.admin;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/Admin/index")
    public String showAddProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "/Admin/index";
    }

    @GetMapping("/Admin/product")
    public String showAddProduct(Model model)
    {
        model.addAttribute("category", new Category());
//        model.addAttribute("categories", productService.getAllCategories());
//        model.addAttribute("products", productService.getAllProduct());
        return "Admin/product";
    }
}
