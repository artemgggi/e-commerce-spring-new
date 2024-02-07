package com.artemgggi.webshop.controller.admin;

import com.artemgggi.webshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminAjaxController {

    private final ProductService productService;

    public AdminAjaxController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/products")
    public ModelAndView returnProducts(Model model) {
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("products", productService.getAllProduct());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/products");
        return mv;
    }

    @GetMapping ("/admin/productList")
    public ModelAndView returnProductsList(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/productList");
        return mv;
    }

    @GetMapping("/admin/orders")
    public ModelAndView returnOrders(Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/orders");
        return mv;
    }
}
