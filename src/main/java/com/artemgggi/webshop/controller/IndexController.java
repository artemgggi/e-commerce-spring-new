package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.repository.ProductRepository;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.model.ShoppingCart;
import com.artemgggi.webshop.model.WishList;
import com.artemgggi.webshop.service.ProductService;
import com.artemgggi.webshop.service.ShoppingCartService;
import com.artemgggi.webshop.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ShoppingCartService shoppingCartService;

    private final WishListService wishListService;

    public IndexController(ProductRepository productRepository,
                           ProductService productService,
                           ShoppingCartService shoppingCartService,
                           WishListService wishListService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.wishListService = wishListService;
    }

    public String getSessionToken(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("sessionToken");
    }

    public String getSessionTokenWishList(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("sessionTokenWishList");
    }

    @GetMapping("/")
    public String showIndex(HttpServletRequest request, Model model) {
        String sessionToken = getSessionToken(request);
        String sessionTokenWishList = getSessionTokenWishList(request);
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        if (sessionTokenWishList == null) {
            model.addAttribute("wishList", new WishList());
        } else {
            WishList wishList = wishListService.getWishListBySessionToken(sessionTokenWishList);
            model.addAttribute("wishList", wishList);
        }
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/index";
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("value") String value) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fragment/searchFragment");
        List<Product> products = productService.searchProductByNameLike(value);
        mv.addObject("products", products);
        return mv;
    }

    @GetMapping("/category/{id}")
    public String getProductByCategory(@PathVariable ("id") String id, HttpServletRequest request, Model model) {
        String sessionToken = getSessionToken(request);
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
        }
        List<Product> products = productService.searchProductByCategory(id);
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/categories";
    }
}
