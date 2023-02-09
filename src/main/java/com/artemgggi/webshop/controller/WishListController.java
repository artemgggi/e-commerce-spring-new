package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@Controller
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @GetMapping("/addToWishlist/{id}")
    public String addToWishList(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionTokenWishList");
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionTokenWishList", sessionToken);
            wishListService.addToWishFirstTime(id, sessionToken);
        } else {
            wishListService.addToExistingWishList(id, sessionToken);
        }
        return "redirect:/";
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
