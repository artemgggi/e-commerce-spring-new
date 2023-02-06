package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.dto.ShoppingCartRepository;
import com.artemgggi.webshop.dto.WishListRepository;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.model.ShoppingCart;
import com.artemgggi.webshop.model.WishList;
import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import com.artemgggi.webshop.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    WishListService wishListService;

    @GetMapping("/")
    public String showIndex(HttpServletRequest request, Model model) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
            model.addAttribute("wishList", new WishList());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            WishList wishList = wishListService.getWishListBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("wishList", wishList);
        }
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView search(@RequestParam("value") String value) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fragment/searchFragment");
        List<Product> products = productService.searchProductByNameLike(value);
        mv.addObject("products", products);
        return mv;
    }
}
