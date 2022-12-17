package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GetViewController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnAddProducts(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addProduct");
        return mv;
    }

    @RequestMapping(value = "/productsList", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnProductsList() {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productRepository.findAll();
        mv.setViewName("productsList");
        mv.addObject("products", products);
        return mv;
    }
}
