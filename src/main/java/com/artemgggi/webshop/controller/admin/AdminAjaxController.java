package com.artemgggi.webshop.controller.admin;

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
public class AdminAjaxController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/Admin/products", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnProducts(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Admin/products");
        return mv;
    }

    @RequestMapping(value = "/Admin/productList", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnProductsList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Admin/productList");
        return mv;
    }

    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnLogin() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/loginForm");
        return mv;
    }
}
