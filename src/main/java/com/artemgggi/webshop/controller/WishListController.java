package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/addToWishlist/{id}")
    public String addToWishList(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
            wishListService.addToWishFirstTime(id, sessionToken);
        } else {
            wishListService.addToExistWishList(id, sessionToken);
        }
        return "redirect:/admin/index";
    }

    @GetMapping("/removeWishListItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionTokenWishList");
        wishListService.removeItemWishList(id, sessionToken);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/clearWishList")
    public String clearShoopiString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionTokenWishList");
        request.getSession(false).removeAttribute("sessionTokenWishList");
        wishListService.clearWishList(sessionToken);
        return "redirect:/shoppingCart";
    }
}
