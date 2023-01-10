package com.artemgggi.webshop.controller.admin;

import com.artemgggi.webshop.dto.CategoryRepository;
import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/index")
    public String showAddProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/index";
    }

    @GetMapping("/admin/product")
    public String showAddProduct(Model model)
    {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("products", productService.getAllProduct());
        return "admin/product";
    }

    @PostMapping("/admin/addProduct")
    public String saveProduct(@RequestParam("file") MultipartFile file,
                              @RequestParam("pname") String name,
                              @RequestParam("price") int price,
                              @RequestParam("desc") String desc,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("category") String category)
    {
        productService.saveProductToDB(file, name, price,desc, quantity, category);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/addCategory")
    public String addCategory(@RequestParam("category") String name) {
        productService.saveCategory(name);
        return "redirect:/admin/index";
    }
}
