package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DetailsController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getProductDetail(@PathVariable("id") Long id, Model model) {
        Product p = productService.getProductById(id);
        model.addAttribute("product", p);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/detail");
        return mv;
    }
}
