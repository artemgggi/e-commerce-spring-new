package com.artemgggi.webshop.controller.admin;

import com.artemgggi.webshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminAjaxController {

    private final ProductService productService;

    public AdminAjaxController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnProducts(Model model) {
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("products", productService.getAllProduct());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/products");
        return mv;
    }

    @RequestMapping(value = "/admin/productList", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnProductsList(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/productList");
        return mv;
    }

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnOrders(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/orders");
        return mv;
    }
}
